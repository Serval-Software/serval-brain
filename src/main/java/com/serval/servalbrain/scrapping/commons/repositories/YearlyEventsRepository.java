package com.serval.servalbrain.scrapping.commons.repositories;

import com.serval.servalbrain.scrapping.commons.YearlyEvents;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YearlyEventsRepository extends MongoRepository<YearlyEvents, Long> { }
