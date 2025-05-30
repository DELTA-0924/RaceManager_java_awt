package io.reflectoring.demo.contact;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DriverTeam {
    private int id;
    private String name;  
    private int age;
    private int experience;
    private String teamName;
    private String specialSkill;
    private int salary;   
}
