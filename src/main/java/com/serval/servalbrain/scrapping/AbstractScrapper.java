package com.serval.servalbrain.scrapping;

import org.jsoup.nodes.Document;

public abstract class AbstractScrapper {

    protected final String url;
    protected Document document;

    public AbstractScrapper(final String url) {
        this.url = url;
    }
    abstract void scrap();
}
