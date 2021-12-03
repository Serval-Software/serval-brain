package com.serval.servalbrain.scrapping;

import com.serval.servalbrain.scrapping.commons.Rescue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class RescueScrapper<T extends Rescue> extends AbstractScrapper<T> {
    public RescueScrapper(String url) {
        super(url);
    }

    @Override
    public T scrap() {
        Document document = null;
        try {
            document = getDocument(url);
        } catch (IOException e) {
            // e.printStackTrace();
        }
        String title = "";
        String pageContent = "";

        if(document != null) {
            Element body = document.getElementById("error-page");
            if(body == null) {
                Elements content = document.getElementById("content").child(0).child(0).children();
                title = content.remove(0).text();
                pageContent = content.text();

                System.out.println(new Rescue(title, pageContent));
            } else {
                System.out.println(String.format("PAGE %s: NULL", url));
            }
        }
        return (T) new Rescue(title, pageContent);
        // TODO
    }
}
