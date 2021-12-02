package com.serval.servalbrain.scrapping.commons;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class YearlyEvents {
    private int year;
    private List<EventUrlPair> events = new ArrayList<>();

    public YearlyEvents(int year) {
        this.year = year;
    }
}
