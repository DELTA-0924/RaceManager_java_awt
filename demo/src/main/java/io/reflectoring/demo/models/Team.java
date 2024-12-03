package io.reflectoring.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Team {
    private int id;
    private String name;
    private String country;
    private double budget;    
}
