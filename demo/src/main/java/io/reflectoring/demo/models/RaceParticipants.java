package io.reflectoring.demo.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RaceParticipants {    
        private int raceId;
        private int carId;
        private int driverId;
        private int navigatorId;
        private int posistion;
        private Double lap_time;
        private int penalties;
        private int points;
        private int teamId;    
}
