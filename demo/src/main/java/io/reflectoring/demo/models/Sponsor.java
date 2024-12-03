package io.reflectoring.demo.models;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Sponsor {
    private int id;
    private String name;
    private String industry;
    private double budget;
 
}
