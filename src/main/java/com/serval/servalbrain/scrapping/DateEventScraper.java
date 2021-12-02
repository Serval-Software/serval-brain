package com.serval.servalbrain.scrapping;

import com.serval.servalbrain.scrapping.commons.EventUrlPair;
import com.serval.servalbrain.scrapping.commons.YearlyEvents;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DateEventScraper extends AbstractScrapper {
    public DateEventScraper(String url) throws IOException {
        super(url);
        document = Jsoup.connect(url).get();
    }

    private EventUrlPair parseEventUrl(Element eventUrlString) {
        final String eventString = eventUrlString.text();
        final String url = eventUrlString.childrenSize() > 0 ? eventUrlString.child(0).attr("href") : "";

        return new EventUrlPair(eventString, url);
    }

    @Override
    void scrap() {
        Elements datedEvents = document.getElementById("content").child(0).child(0).children();
        List<YearlyEvents> events = new ArrayList<>();
        YearlyEvents currentYearlyEvents = new YearlyEvents();
        int year = -1;

        datedEvents.remove(0); // h1 field;
        datedEvents.remove(0); // first paragraph
        datedEvents.remove(0); // second paragraph

        for(Element el: datedEvents) {
            try {
                year = Integer.parseInt(el.text());
            } catch(NumberFormatException e) {
                year = -1;
            }

            if(year == -1) {
                currentYearlyEvents.getEvents().add(parseEventUrl(el));
            } else {
                events.add(currentYearlyEvents);
                currentYearlyEvents = new YearlyEvents(year);
            }
        }

        System.out.println(events);

        final String url = events.get(2).getEvents().get(0).getUrl();
        try {
            new RescueScrapper(url).scrap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new DateEventScraper("https://sauveteurdudunkerquois.fr/ephemeride-du-sauvetage/").scrap();
    }
}
