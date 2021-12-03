package com.serval.servalbrain.scrapping.commons;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document
public class Rescue {
    private @Id long id;
    private String title;
    private String text;

    public Rescue(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
