package io.reflectoring.demo.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Race {
    private int id;
    private String name;
    private String type_of;
    private Date date_of;
    private int track_id;
    private int championship_id;
    private String weatherCondition;
}
