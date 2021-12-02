package com.serval.servalbrain.scrapping;

import org.jsoup.nodes.Document;

public abstract class AbstractScrapper {

    private final String url;
    private Document document;

    public AbstractScrapper(final String url) {
        this.url = url;
    }
    abstract void scrap();
}
