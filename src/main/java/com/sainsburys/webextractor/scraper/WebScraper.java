package com.sainsburys.webextractor.scraper;

import com.sainsburys.webextractor.exception.ScraperException;
import com.sainsburys.webextractor.model.ProductInfo;

/**
 * Interface for a web scraper
 */
public interface WebScraper {

    String scrape() throws ScraperException;
    ProductInfo getProductInfo(String url);
}
