package io.reflectoring.demo.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CarTeam{
    private int id;
    private String brand;
    private String model;
    private String year;
    private String teamName;
    private Double price;
    private Double maintenance_cost;
}
