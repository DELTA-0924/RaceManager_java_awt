package io.reflectoring.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Navigator {
    private int id;
    private String name;
    private int age;
    private int experience;
    private int team_id;
    private int salary;
}
