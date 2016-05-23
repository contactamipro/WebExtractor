package com.sainsburys.webextractor;

import com.sainsburys.webextractor.exception.ScraperException;
import com.sainsburys.webextractor.scraper.WebScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command line runner, so we can run Spring Boot application
 */
@Component
public class Runner implements CommandLineRunner {

    private static Logger logger = Logger.getLogger(Runner.class.getName());

    @Autowired
    private WebScraper scraper;

    @Override
    public void run(String... args) throws Exception {

        try {
            System.out.println(scraper.scrape());
        } catch (ScraperException scrapEx) {
            logger.log(Level.SEVERE, "Exception during scrapping process: ", scrapEx);

        }
        catch (Exception genEx) {
            logger.log(Level.SEVERE, "General Exception: ", genEx);

        }

    }
}
