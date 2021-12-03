package com.serval.servalbrain.api;

import com.serval.servalbrain.db.SequenceGenerator;
import com.serval.servalbrain.scrapping.AbstractScrapper;
import com.serval.servalbrain.scrapping.DateEventScraper;
import com.serval.servalbrain.scrapping.ScrapperFactory;
import com.serval.servalbrain.scrapping.ScrapperType;
import com.serval.servalbrain.scrapping.commons.YearlyEvents;
import com.serval.servalbrain.scrapping.commons.repositories.YearlyEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class MainController {

    @Autowired
    private YearlyEventsRepository repository;
    @Autowired
    private MongoTemplate template;
    @Autowired
    private SequenceGenerator sequenceGenerator;

    @GetMapping("/message")
    public String getMessage() {
        return "It is working!";
    }

    @PostMapping("/scrap")
    public String x(@RequestBody String url) {
        DateEventScraper scrapper = (DateEventScraper) new ScrapperFactory().getScrapper(ScrapperType.DATE_EVENT_SCRAPPER, url);
        //.stream().forEach(repository::save);
        repository.saveAll(scrapper.scrap().stream().map(e -> { ((YearlyEvents) e).setId(sequenceGenerator.generateSequence("yevt_sequence")); return e; }).toList());
        return repository.findAll().stream().map(YearlyEvents::toString).collect(Collectors.joining());
    }
}
