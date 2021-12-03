package com.serval.servalbrain.scrapping.commons;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document
public class EventUrlPair {
    private @Id long id;
    private String event;
    private String url;

    public EventUrlPair(String event, String url) {
        this.event = event;
        this.url = url;
    }
}
