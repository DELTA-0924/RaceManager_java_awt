package io.reflectoring.demo.DataAccess.DAO;
import java.util.List;
import java.util.ArrayList;

import io.reflectoring.demo.DataAccess.DatabaseConnection;
import io.reflectoring.demo.models.Track;
import java.sql.*;

public class TrackDAO {
   public List<Track>getTracks() throws SQLException{
        List<Track>tracks=new ArrayList<>();
        String query="select * from Tracks";
        try(Connection connection =DatabaseConnection.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultset=statement.executeQuery(query)){
            while(resultset.next()){
                tracks.add(new Track(resultset.getInt("id"),resultset.getString("name"),resultset.getString("location"),
                                    resultset.getInt("length_of"),resultset.getString("surface_type"),
                                    resultset.getInt("turn_count"),resultset.getInt("elevation_change")
                ));                
            }
        }
        return tracks;
    }
    public void addTracks(Track track)throws SQLException{
        String query="insert into tracks(name,location,length_of,surface_type,turn_count,elevation_change) values(?,?,?,?,?,?)";
        try(Connection connection=DatabaseConnection.getConnection();
            PreparedStatement statement=connection.prepareStatement(query)){
                statement.setString(1, track.getName());
                statement.setString(2,track.getLocation());
                statement.setInt(3,track.getLength_of());
                statement.setString(4,track.getSurface_type());
                statement.setInt(5,track.getTurn_count());
                statement.setInt(6,track.getElevation_change());             
                statement.executeUpdate();
            }
    }
}
