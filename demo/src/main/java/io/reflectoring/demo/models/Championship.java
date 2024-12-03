package io.reflectoring.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Championship {
    private int id ;
    private String name;
    private int  season;
    private String startDate;
    private String endDate;
    private String location;
    private int winner;
    private int prizeMoney;
}
