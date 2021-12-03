package com.serval.servalbrain.scrapping.commons;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class YearlyEvents {
    private @Id long id;
    private int year;
    private List<EventUrlPair> events = new ArrayList<>();

    public YearlyEvents(int year) {
        this.year = year;
    }
}
