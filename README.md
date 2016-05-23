# WebExtractor
Web Extractor is a Console Spring Boot application which scraps information from a given website. This application can be easily extended to scrap other sites. 

The implementation for Sainsburys outputs a JSON array of all the products on their page.

# Running the application
The simplest way to run the application is to clone the repository, and use gradle to compile and run it:

    cd /webscraper
    git clone https://github.com/contactamipro/WebExtractor.git
    cd webscraper
    gradle build
    gradle bootrun

The build will be successful only if all tests pass successfully. Build process will also create a standalone jar.


# Testing
To execute unit tests run:

`gradle test`

# Design notes
The application uses Spring Boot and uses IOC to wire the scraper. The URI is picked up from application.properties. We can easily extend this application to be a micro service and implement scrapers for other sites.