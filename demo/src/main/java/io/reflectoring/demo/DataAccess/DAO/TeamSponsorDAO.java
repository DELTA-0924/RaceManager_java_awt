package io.reflectoring.demo.DataAccess.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.reflectoring.demo.DataAccess.DatabaseConnection;

public class TeamSponsorDAO {
    public void setTeamSponsor(int team_id,int sponsor_Id,double budget)  throws SQLException{
        String query="insert into team_sponsors(team_id,sponsor_id,contribution)values(?,?,?)";
        try(Connection connection=DatabaseConnection.getConnection();
            PreparedStatement statement=connection.prepareStatement(query)){
                statement.setInt(1,team_id);
                statement.setInt(2,sponsor_Id);
                statement.setDouble(3,budget);
                statement.executeUpdate();
        }        
    }
}
