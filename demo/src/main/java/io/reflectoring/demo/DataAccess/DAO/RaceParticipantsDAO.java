package io.reflectoring.demo.DataAccess.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.reflectoring.demo.DataAccess.DatabaseConnection;
import io.reflectoring.demo.contact.RaceParticipantsCotact;
import io.reflectoring.demo.models.RaceParticipants;

public class RaceParticipantsDAO{
   public List<RaceParticipantsCotact> getparticipants()throws SQLException{
        List<RaceParticipantsCotact>participants=new ArrayList<>();
        String query="select r.id,r.name as racename,c.model as carname,d.name as drivername,n.name as navigatorname,position,lap_time,penalties,points,t.name as teamname\n" + //
                        "from race_participants as rpt\n" + //
                        "join races as r on r.id=rpt.race_id\n" + //
                        "join cars as c on c.id=rpt.car_id\n" + //
                        "join drivers as d on d.id=rpt.driver_id\n" + //
                        "join navigators as n on n.id=rpt.navigator_id\n" + //
                        "join teams as t on t.id=rpt.team_id;";
        try(Connection connection=DatabaseConnection.getConnection();
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(query)){
            while(resultSet.next()){
                participants.add(new RaceParticipantsCotact(
                    resultSet.getInt("id"),
                    resultSet.getString("racename"),resultSet.getString("carname"),resultSet.getString("drivername"),
                    resultSet.getString("navigatorname"),resultSet.getInt("position"),resultSet.getDouble("lap_time"),
                    resultSet.getInt("penalties"),resultSet.getInt("points"),resultSet.getString("teamname")
                ));
            }
        }
        return participants;
    }
 public   void addParticipants(RaceParticipants participant)throws SQLException{
        String query="insert into race_participants(race_id,car_id,driver_id,navigator_id,position,lap_time,penalties,points,team_id) "+//
        "values(?,?,?,?,?,?,?,?,?)";
        try(Connection connection =DatabaseConnection.getConnection();
            PreparedStatement statement=connection.prepareStatement(query)){
                statement.setInt(1, participant.getRaceId());
                statement.setInt(2, participant.getCarId());
                statement.setInt(3, participant.getDriverId());
                statement.setInt(4, participant.getNavigatorId());
                statement.setInt(5, participant.getPosistion());
                statement.setDouble(6, participant.getLap_time());
                statement.setInt(7, participant.getPenalties());
                statement.setInt(8, participant.getPoints());
                statement.setInt(9, participant.getTeamId());
                statement.executeUpdate();                    
        }
    }
}