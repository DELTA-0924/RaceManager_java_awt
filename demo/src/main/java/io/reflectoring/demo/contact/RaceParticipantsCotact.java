package io.reflectoring.demo.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RaceParticipantsCotact {
    private int id;
    private String raceName;
    private String carName;
    private String driverName;
    private String navigatorName;
    private int posistion;
    private Double lap_time;
    private int penalties;
    private int points;
    private String teamName;

}
