package com.sainsburys.webextractor.scraper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sainsburys.webextractor.exception.ScraperException;
import com.sainsburys.webextractor.model.ProductInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implementation of scraper for Sainsburys page
 */
@Component
public class SainsburysWebScraper implements WebScraper {

    private static Logger logger = Logger.getLogger(SainsburysWebScraper.class.getName());

    @Value("${sainsburys.url}")
    private  String url;
    private Connection connection;

    /**
     * Fetch information for product using its url
     * and returns ProductInfo
     */
    public ProductInfo getProductInfo(String url) {
        String title = "";
        BigDecimal size = null;
        BigDecimal unitPrice = null;
        String description = "";
        BigDecimal kbValue = new BigDecimal(1024);

        try {
            Document doc = Jsoup.connect(url).get();
            Element el = doc.select("div.productTitleDescriptionContainer").first();
            if (el == null) {
                logger.log(Level.SEVERE, "Could not process product url: " + url);
                return null;
            } else {
                //get title
                Element titleElement = el.getElementsByTag("h1").first();
                title = titleElement.text();

                //size of page (kb)
                size = new BigDecimal(doc.toString().length());
                size = size.divide(kbValue).setScale(2, RoundingMode.HALF_UP);
            }

            //price per unit
            el = doc.select("p.pricePerUnit").first();
            if (el == null) {
                return null;
            } else {
                String pTxt = el.text().replace("/unit", "").replace("£", "");
                unitPrice = new BigDecimal(pTxt);
            }

            //get description
            el = doc.select("div.productText").first();
            if (el == null) {
                return null;
            } else {
                description = el.text();
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return new ProductInfo(title, size, unitPrice, description);
    }

    /**
     * Method that does all the work of scraping
     */
    public String scrape() throws ScraperException {
        JSONObject json = new JSONObject();
        JSONArray results = new JSONArray();
        json.put("results", results);
        BigDecimal total = new BigDecimal(0.0); // total unit price.

        Connection con = Jsoup.connect(url);
        if (con == null) {
            throw new ScraperException("Could not connect to the URL: " + url);
        }

        try {
            Element el = con.get().select("ul.productLister").first();
            if (el == null) { //no products available
                throw new ScraperException("No products available: " + url);
            }

            Elements els = el.getElementsByTag("li");
            for (Element element : els) {
                Element pInfoEl = element.select("div.productInfo").first();
                Element linkEl = pInfoEl.getElementsByTag("a").first();

                String infoUrl = linkEl.attr("href");
                ProductInfo pInfo = getProductInfo(infoUrl);

                // Add JSON representation of the ProductInfo object to the array.
                if (pInfo != null) {
                    results.add(pInfo.toJSON());
                    total = total.add(pInfo.getUnitPrice());
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "IOExceeption occurred", ex);
        }

        // Set the total price /unit for all products.
        json.put("total", total.doubleValue());

        return json.toJSONString();
    }
}
