package io.reflectoring.demo.DataAccess.DAO;
import java.sql.*;
import java.text.ParseException;

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
                        resultSet.getDouble("price"),
                        resultSet.getDouble("maintenance_cost")
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
        String query = "INSERT INTO cars ( model, brand, year,team_id, maintenance_cost, price) VALUES ( ?, ?, ?, ?, ?, ?)";                        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, car.getModel());
            statement.setString(2, car.getBrand());
            statement.setInt(3, Integer.parseInt(car.getYear()));            
            statement.setInt(4, car.getTeam_id());
            statement.setDouble(5, car.getMaintenance_cost());
            statement.setDouble(6, car.getPrice());
            statement.executeUpdate();
        }
    }
    public void updateCar(int id, int column, String newValue) {
        String columnName = getColumnName(column);
        String query = "UPDATE cars SET " + columnName + " = ? WHERE id = ?";
        
    
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
                if((!columnName.equals("brand"))&&(!columnName.equals("model")))   {             
                    try{
                        statement.setInt(1, Integer.parseInt(newValue));
                    }catch(NumberFormatException e){
                        statement.setDouble(1, Double.parseDouble(newValue));
                    }
                

                }
                else 
                statement.setString(1, newValue);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteFromDatabase(int id) {
        String query = "DELETE FROM cars WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Метод для получения имени столбца по индексу
    private String getColumnName(int column) {
        switch (column) {
            case 1: return "model";
            case 2: return "brand";
            case 3: return "year";
            case 4: return "team_id";
            case 5: return "price";
            case 6: return "maintenance_cost";
            default: return "";
        }
    }
    
}
