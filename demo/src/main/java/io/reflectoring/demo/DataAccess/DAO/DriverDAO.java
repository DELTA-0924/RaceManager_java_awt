package io.reflectoring.demo.DataAccess.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import io.reflectoring.demo.DataAccess.DatabaseConnection;
import io.reflectoring.demo.contact.DriverTeam;
import io.reflectoring.demo.models.Driver;
public class DriverDAO {
    public List<DriverTeam> getDrivers() throws SQLException {
        List<DriverTeam> drivers = new ArrayList<>();
        String query = "select d.id,d.name,age,experience,t.name as teamname,special_skill,salary from drivers as d\n" + //
                        "join teams  as t on t.id=d.team_id;";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                drivers.add(new DriverTeam(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("experience"),
                        resultSet.getString("teamname"),
                        resultSet.getString("special_skill"),
                        resultSet.getInt("salary")
                ));
            }
        }
        return drivers;
    }
    public List<DriverTeam> getDriversbyTeam(int id) throws SQLException {
        List<DriverTeam> drivers = new ArrayList<>();
        String query = "select d.id,d.name from drivers as d\n" + //
                        "where d.team_id=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);            
            ) {
            statement.setInt(1,id);
            try(ResultSet resultSet = statement.executeQuery()){            
                while (resultSet.next()) {
                    drivers.add(new DriverTeam(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),0,0,"","",0
                            )

                    );
                }            
            }
        }
        return drivers;
    }
    public void updateDriver(int column,int id,String newValue) throws SQLException{
        String columnName=getColumnName(column);
        String query="update drivers set "+columnName+"=? where id=?";
        try(Connection connection=DatabaseConnection.getConnection();
            PreparedStatement statement=connection.prepareStatement(query)){
            if(columnName.equals("id")||columnName.equals("age")|| columnName.equals("salary") || columnName.equals("team_id"))
            {
                statement.setInt(1,Integer.parseInt( newValue));

            }else {
                statement.setString(1,newValue);
            }
            statement.setInt(2,id);
            statement.executeUpdate();
        }
    }
    public void addDriver(Driver driver) throws SQLException {
        String query = "INSERT INTO drivers (name, age, experience, team_id, special_skill, salary) VALUES ( ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {            
            statement.setString(1, driver.getName());
            statement.setInt(2, driver.getAge());
            statement.setInt(3, driver.getExperience());
            statement.setInt(4, driver.getTeam_id());
            statement.setString(5, driver.getSpecialSkill());
            statement.setDouble(6, driver.getSalary());
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
                return "age";                
            case 4:
                return "experience";
            case 5:
                return "team_id";
            case 6:
                return "special_skill";
            case 7:
                return "salary";
            default:
                return "";
        }
    }
}
