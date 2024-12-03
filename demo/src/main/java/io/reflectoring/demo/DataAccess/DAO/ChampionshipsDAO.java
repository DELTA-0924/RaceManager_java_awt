package io.reflectoring.demo.DataAccess.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reflectoring.demo.DataAccess.DatabaseConnection;
import io.reflectoring.demo.models.Championship;
public class ChampionshipsDAO {
   public  List<Championship>getChampionships()throws SQLException{
        List<Championship> champList=new ArrayList<Championship>();
        String query="select * from championships";
        try(Connection connection=DatabaseConnection.getConnection();
            Statement statement=connection.createStatement();
            ResultSet result=statement.executeQuery(query)){
            while(result.next()){
                champList.add(new Championship(result.getInt("id"),result.getString("name"),
                                        result.getInt("season"),result.getString("start_date"),
                                        result.getString("end_date"),result.getString("location"),
                                        result.getInt("winner_id"),result.getInt("prize_money")
                                        ) 
                );
            }
        }
        return champList;
    }
   public  void addChampionship(Championship champ)throws SQLException,ParseException{
        String query="insert into championships (name,season,start_date,end_date,location,winner_id,prize_money) values(?,?,?,?,?,?,?)";
        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
        Date startDate=format.parse(champ.getStartDate());
        Date endDate=format.parse(champ.getEndDate());
        java.sql.Date sqlDateEndDate=new java.sql.Date(endDate.getTime());
        java.sql.Date sqlDateStartDate=new java.sql.Date(startDate.getTime());
        try(Connection connection=DatabaseConnection.getConnection();
            PreparedStatement statement=connection.prepareStatement(query)){
            statement.setString(1, champ.getName());
            statement.setInt(2, champ.getSeason());
            statement.setDate(3, sqlDateStartDate);
            statement.setDate(4, sqlDateEndDate);
            statement.setString(5, champ.getLocation());
            statement.setInt(6, champ.getWinner());
            statement.setInt(7, champ.getPrizeMoney());
            statement.executeUpdate();
        }
    }
    public void calculateChampionWinner(int id)throws SQLException{
        String functionCall = "SELECT  get_track_by_id(?)";
           try (Connection connection = DatabaseConnection.getConnection();
           PreparedStatement statement = connection.prepareStatement(functionCall)) {        
            statement.setInt(1, id);                        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
