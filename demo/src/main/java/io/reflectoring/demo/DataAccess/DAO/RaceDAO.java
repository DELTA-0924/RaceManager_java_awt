package io.reflectoring.demo.DataAccess.DAO;
import java.sql.*;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;

import io.reflectoring.demo.DataAccess.DatabaseConnection;
import io.reflectoring.demo.contact.RaceChampTrack;
import io.reflectoring.demo.models.Race;

public class RaceDAO {
    public List<RaceChampTrack> getRaces() throws SQLException {
        List<RaceChampTrack> races = new ArrayList<>();
        String query = "select r.id,r.name,type_of,date_of,t.name as trackname,c.name as championname,weather_conditions from races as r\n" + //
                        "join championships as c on c.id=r.championship_id\n" + //
                        "join tracks as t on t.id=r.track_id;";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                races.add(new RaceChampTrack(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("type_of"),
                        resultSet.getString("date_of"),
                        resultSet.getString("trackname"),
                        resultSet.getString("championname"),
                        resultSet.getString("weather_conditions")
                ));
            }
        }
        return races;
    }

    public void addRace(Race race) throws SQLException,ParseException {
        String query = "INSERT INTO races (id, name, type_of, date_of, track_id, championship_id, weather_conditions) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        
        
        try (Connection connection = DatabaseConnection.getConnection();

             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, race.getId());
            statement.setString(2, race.getName());
            statement.setString(3, race.getType_of());
            statement.setDate(4, race.getDate_of());
            statement.setInt(5, race.getTrack_id());
            statement.setInt(6, race.getChampionship_id());
            statement.setString(7, race.getWeatherCondition());
            statement.executeUpdate();
        }
    }
}
