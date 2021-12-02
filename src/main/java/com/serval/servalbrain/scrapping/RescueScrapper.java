package com.serval.servalbrain.scrapping;

import com.serval.servalbrain.scrapping.commons.Rescue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class RescueScrapper extends AbstractScrapper {
    public RescueScrapper(String url) throws IOException {
        super(url);
        document = Jsoup.connect(url).get();
    }

    @Override
    void scrap() {
        Element body = document.getElementById("error-page");
        if(body == null) {
            Elements content = document.getElementById("content").child(0).child(0).children();
            //System.out.println(content);
            final String title = content.remove(0).text();
            final String pageContent = content.text();

            System.out.println(new Rescue(title, pageContent));
        } else {
            System.out.println(String.format("PAGE %s: NULL", url));
        }

    }
}
