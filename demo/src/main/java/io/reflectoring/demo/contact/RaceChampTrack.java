package io.reflectoring.demo.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class RaceChampTrack {
    private int id;
    private String name;
    private String type_of;
    private String date_of;
    private String trackName;
    private String championshipName;
    private String weatherCondition;
}
