package io.reflectoring.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Track {
    private int id;
    private String name;
    private String location;
    private int length_of;
    private String surface_type;
    private int turn_count;
    private int elevation_change;
}
