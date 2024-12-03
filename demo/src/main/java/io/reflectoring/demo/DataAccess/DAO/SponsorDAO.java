package io.reflectoring.demo.DataAccess.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import io.reflectoring.demo.DataAccess.DatabaseConnection;
import io.reflectoring.demo.models.Sponsor;

public class SponsorDAO {
    public List<Sponsor> getSponsors() throws SQLException {
        List<Sponsor> sponsors = new ArrayList<>();
        String query = "SELECT * FROM sponsors";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                sponsors.add(new Sponsor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("industry"),
                        resultSet.getDouble("budget")
                ));
            }
        }
        return sponsors;
    }

    public void addSponsor(Sponsor sponsor) throws SQLException {
        String query = "INSERT INTO sponsors ( name, industry, budget) VALUES ( ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {            
            statement.setString(1, sponsor.getName());
            statement.setString(2, sponsor.getIndustry());
            statement.setDouble(3, sponsor.getBudget());
            statement.executeUpdate();
        }
    }
}
