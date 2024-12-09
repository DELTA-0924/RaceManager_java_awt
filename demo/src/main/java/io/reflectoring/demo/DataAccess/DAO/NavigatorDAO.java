package io.reflectoring.demo.DataAccess.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import io.reflectoring.demo.DataAccess.DatabaseConnection;
import io.reflectoring.demo.contact.DriverTeam;
import io.reflectoring.demo.contact.NavigatorTeam;
import io.reflectoring.demo.models.Navigator;

public class NavigatorDAO {
    public List<NavigatorTeam> getNavigators() throws SQLException {
        List<NavigatorTeam> navigators = new ArrayList<>();
        String query = "select n.id,n.name,age,experience,t.name as teamName,salary from navigators as n\n" + //
                        "join teams  as t on t.id=n.team_id;";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                navigators.add(new NavigatorTeam(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("experience"),
                        resultSet.getString("teamname"),
                        resultSet.getInt("salary")
                ));
            }
        }
        return navigators;
    }
   public List<NavigatorTeam> getNavigatorsbyTeam(int id) throws SQLException {
    List<NavigatorTeam> navigators = new ArrayList<>();
        String query = "select d.id,d.name from navigators as d\n" + //
                        "where d.team_id=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);            
            ) {
            statement.setInt(1,id);
            try(ResultSet resultSet = statement.executeQuery()){            
                while (resultSet.next()) {
                    navigators.add(new NavigatorTeam(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),0,0,"",0
                            )

                    );
                }            
            }
        }
        return navigators;
    }
    public void addNavigator(Navigator navigator) throws SQLException {
        String query = "INSERT INTO navigators (name, age, experience, team_id, salary) VALUES ( ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {            
            statement.setString(1, navigator.getName());
            statement.setInt(2, navigator.getAge());
            statement.setInt(3, navigator.getExperience());
            statement.setInt(4, navigator.getTeam_id());
            statement.setDouble(5, navigator.getSalary());
            statement.executeUpdate();
        }
    }
    public void updateDriver(int column,int id,String newValue) throws SQLException{
        String columnName=getColumnName(column);
        String query="update navigators set "+columnName+"=? where id=?";
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
                return "salary";
            default:
                return "";
        }
    }
}
