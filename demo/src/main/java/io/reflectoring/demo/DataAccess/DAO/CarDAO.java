package io.reflectoring.demo.DataAccess.DAO;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.reflectoring.demo.DataAccess.DatabaseConnection;
import io.reflectoring.demo.contact.CarTeam;
import io.reflectoring.demo.models.Car;

public class CarDAO {
    public List<CarTeam> getCars() throws SQLException {
        List<CarTeam> cars = new ArrayList<>();
        String query = "select c.id,model,brand,year,t.name as teamname,price,maintenance_cost from cars as c join teams as t on c.team_id=t.id;";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                cars.add(new CarTeam(
                        resultSet.getInt("id"),
                        resultSet.getString("model"),
                        resultSet.getString("brand"),
                        resultSet.getString("year"),                        
                        resultSet.getString("teamname"),
                        resultSet.getDouble("maintenance_cost"),
                        resultSet.getDouble("price")
                ));
            }
        }
        return cars;
    }
    public List<CarTeam> getCarsbyTeam(int id) throws SQLException {
        List<CarTeam> cars = new ArrayList<>();
        String query = "select c.id,c.model  from cars as c where c.team_id=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    cars.add(new CarTeam(
                            resultSet.getInt("id"),
                            "",
                            resultSet.getString("model"),
                            "",                        
                            "",
                            0.0,
                            0.0
                    ));
                }
            }   
        }
        return cars;
    }

    public void addCar(Car car) throws SQLException,ParseException {
        String query = "INSERT INTO cars (id, model, brand, year,team_id, maintenance_cost, price) VALUES (?, ?, ?, ?, ?, ?, ?,)";
        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date year=format.parse(car.getYear());
        Date sqlYear=new Date(year.getTime());
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, car.getId());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getBrand());
            statement.setDate(4, sqlYear);            
            statement.setInt(5, car.getTeam_id());
            statement.setDouble(6, car.getMaintenance_cost());
            statement.setDouble(7, car.getPrice());
            statement.executeUpdate();
        }
    }
}
