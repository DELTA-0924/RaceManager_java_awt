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
    public void updateSponsor(int id,int column,String newValue) throws SQLException{
        String columnName=getColumnName(column);
        String query="update sponsors set"+columnName+"=? where id=?";
        try(Connection connection=DatabaseConnection.getConnection();
            PreparedStatement statement=connection.prepareStatement(query) ){
                if(columnName.equals("budget") || columnName.equals("id")){
                    statement.setDouble(1,Double.parseDouble(newValue));

                }else{
                    statement.setString(1,newValue);
                }
            statement.setInt(2,id);
            statement.executeUpdate();
        }
    }
    public void deleteFromDatabase(int id) {
        String query = "DELETE FROM sponsors WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private String getColumnName(int column){
        switch(column){
            case 1:
                return "id";
            case 2:
                return "name";
            case 3:
                return "industry";
            case 4:
                return "budget";
            default:
                return "";
        }
    }
}
