package com.sainsburys.webextractor.scraper;

import com.sainsburys.webextractor.Application;
import com.sainsburys.webextractor.exception.ScraperException;
import com.sainsburys.webextractor.model.ProductInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Some unit tests to ensure functionality works
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class SainsburysWebScraperTest {

    @Autowired
    private SainsburysWebScraper scraper;

    public SainsburysWebScraperTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testScrapeNotEmpty() throws ScraperException{
        String json = scraper.scrape();
        assertTrue(!json.isEmpty());
    }

    @Test
    public void testScrapeHasTotalAndResults() throws ScraperException {
        String json = scraper.scrape();
        assertTrue(json.contains("total") && json.contains("results"));
    }

    @Test
    public void testGetProductInfoReturnsNull() {
        String adr = "http://www.xxx.com";
        ProductInfo product = scraper.getProductInfo(adr);
        assertNull(product);
    }

    @Test
    public void testGetProductInfoReturnsObject() {
        String pUrl = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/sainsburys-avocado-xl-pinkerton-loose-300g.html";
        ProductInfo product = scraper.getProductInfo(pUrl);

        assertNotNull(product);
        assertNotNull(product.getTitle());
        assertTrue(product.getSize().doubleValue() > 0);
        assertTrue(product.getUnitPrice().doubleValue() == 1.5);
        assertNotNull(product.getDescription());
        assertTrue(product.getDescription().length() > 0);
    }

    @Test
    public void testScrapeReturnsProductInfos() throws ScraperException {
        String json = scraper.scrape();
        assertTrue(json.contains("Avocado"));
    }

}
