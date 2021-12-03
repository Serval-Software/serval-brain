package com.serval.servalbrain.scrapping;

public class ScrapperFactory {
    public AbstractScrapper getScrapper(final ScrapperType type, final String url) {
        return switch(type) {
            case DATE_EVENT_SCRAPPER -> new DateEventScraper(url);
            default -> new RescueScrapper(url);
        };
    }
}
