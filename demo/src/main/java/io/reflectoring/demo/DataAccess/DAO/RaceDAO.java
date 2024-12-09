package io.reflectoring.demo.DataAccess.DAO;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public List<RaceChampTrack> getRacesId() throws SQLException {
        List<RaceChampTrack> races = new ArrayList<>();
        String query = "select r.id,r.name from races as r\n" + //
                        "join championships as c on c.id=r.championship_id\n" + //
                        "join tracks as t on t.id=r.track_id;";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                races.add(new RaceChampTrack(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        "",
                        "",
                        "",
                        "",
                        ""
                ));
            }
        }
        return races;
    }
    public void addRace(Race race) throws SQLException,ParseException {
        String query = "INSERT INTO races ( name, type_of, date_of, track_id, championship_id, weather_conditions) VALUES ( ?, ?, ?, ?, ?, ?)";
        
        
        
        try (Connection connection = DatabaseConnection.getConnection();

             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, race.getName());
            statement.setString(2, race.getType_of());
            statement.setDate(3, race.getDate_of());
            statement.setInt(4, race.getTrack_id());
            statement.setInt(5, race.getChampionship_id());
            statement.setString(6, race.getWeatherCondition());
            statement.executeUpdate();
        }
    }
    private void updateRace(String newValue,int column,int id)throws SQLException{
        String columnName=getColumnName(column);
        String query="update race set "+columnName+"=? where id=?";
        try(Connection connection=DatabaseConnection.getConnection();
            PreparedStatement statement=connection.prepareStatement(query)){
            if(columnName.equals("track_id")||columnName.equals("championship_id")){
                statement.setInt(1,Integer.parseInt(newValue));                
            }else if( columnName.equals("date_of")){
                try{
                SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
                java.util.Date tempDate=format.parse(newValue);
                Date sqlDate=new Date(tempDate.getTime());
                statement.setDate(1,sqlDate);
                }
                catch(ParseException ex){
                    ex.printStackTrace();
                }
            }
            else {
                statement.setString(1,newValue);

            }
            statement.setInt(2,id);
            statement.executeUpdate();
        }   
    }
    private String getColumnName(int column){
        switch(column){
            case 1:
                return "id";
            case 2:
                return "name";
            case 3:
                return "type_of";
            case 4:
                return  "date_of";
            case 5:
                return "track_id";
            case 6:
                return "championship_id";
            case 7:
                return "weaather_conditions";
            default:
                return "";
        }
    }
}
