package com.serval.servalbrain.api;

import com.serval.servalbrain.db.SequenceGenerator;
import com.serval.servalbrain.scrapping.AbstractScrapper;
import com.serval.servalbrain.scrapping.DateEventScraper;
import com.serval.servalbrain.scrapping.RescueScrapper;
import com.serval.servalbrain.scrapping.ScrapperFactory;
import com.serval.servalbrain.scrapping.ScrapperType;
import com.serval.servalbrain.scrapping.commons.EventUrlPair;
import com.serval.servalbrain.scrapping.commons.Rescue;
import com.serval.servalbrain.scrapping.commons.YearlyEvents;
import com.serval.servalbrain.scrapping.commons.repositories.RescueRepository;
import com.serval.servalbrain.scrapping.commons.repositories.YearlyEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class MainController {

    @Autowired
    private YearlyEventsRepository yearlyRepository;
    @Autowired
    private RescueRepository rescueRepository;
    @Autowired
    private MongoTemplate template;
    @Autowired
    private SequenceGenerator sequenceGenerator;

    @GetMapping("/message")
    public String getMessage() {
        return "It is working!";
    }

    @PostMapping("/scrap")
    public String getDateEvent(@RequestBody String url) {
        DateEventScraper scrapper = (DateEventScraper) new ScrapperFactory().getScrapper(ScrapperType.DATE_EVENT_SCRAPPER, url);
        //.stream().forEach(repository::save);
        yearlyRepository.saveAll(scrapper.scrap().stream().map(e -> { ((YearlyEvents) e).setId(sequenceGenerator.generateSequence("yevt_sequence")); return e; }).toList());
        return yearlyRepository.findAll().stream().map(YearlyEvents::toString).collect(Collectors.joining());
    }

    @PostMapping("/scrap-rescue")
    public List<Rescue> getRescue(@RequestBody String url) {
        List<String> rtr = new ArrayList<>();
        DateEventScraper scrapper = (DateEventScraper) new ScrapperFactory().getScrapper(ScrapperType.DATE_EVENT_SCRAPPER, url);
        
        List<YearlyEvents> events = scrapper.scrap();
        events.stream()
            .flatMap(e -> e.getEvents().stream())
            .map(EventUrlPair::getUrl)
            .toList()
            .forEach(a -> {
                if(isValid(a)) {
                    final RescueScrapper rescueScrapper = (RescueScrapper) new ScrapperFactory().getScrapper(ScrapperType.RESCUE_SCRAPER, a);
                    Rescue rescue = rescueScrapper.scrap();
                    rescue.setId(sequenceGenerator.generateSequence("rescue_sequence"));
                    rescueRepository.save(rescue);
                }
            });
        return rescueRepository.findAll();
    }


    /* Returns true if url is valid */
    public static boolean isValid(String url)
    {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }
            
        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }


}
