package com.serval.servalbrain.scrapping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public abstract class AbstractScrapper<T> {
    protected final String url;

    public AbstractScrapper(String url) {
        this.url = url;
    }

    protected Document getDocument(final String url) throws IOException {
        System.out.println(url);
        return Jsoup.connect(url).get();
    }
    abstract public T scrap();
}
