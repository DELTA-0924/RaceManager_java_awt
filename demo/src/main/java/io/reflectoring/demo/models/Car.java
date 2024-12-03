package io.reflectoring.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Car{
    private int id;
    private String brand;
    private String model;
    private String year;
    private int team_id;
    private Double price;
    private Double maintenance_cost;
}