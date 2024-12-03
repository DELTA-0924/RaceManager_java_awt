package io.reflectoring.demo.DataAccess.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import io.reflectoring.demo.DataAccess.DatabaseConnection;
import io.reflectoring.demo.models.Team;

public class TeamDAO {
    public List<Team> getTeams() throws SQLException {
        List<Team> teams = new ArrayList<>();
        String query = "SELECT * FROM teams";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                teams.add(new Team(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("country"),
                        resultSet.getDouble("budget")
                ));
            }
        }
        return teams;
    }

    public void addTeam(Team team) throws SQLException {
        String query = "INSERT INTO teams ( name, country, budget) VALUES ( ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {            
            statement.setString(1, team.getName());
            statement.setString(2, team.getCountry());
            statement.setDouble(3, team.getBudget());
            statement.executeUpdate();
        }
    }
}
