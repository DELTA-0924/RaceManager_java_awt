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
    public List<Track>getTracksId() throws SQLException{
        List<Track>tracks=new ArrayList<>();
        String query="select id,name from Tracks";
        try(Connection connection =DatabaseConnection.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultset=statement.executeQuery(query)){
            while(resultset.next()){
                tracks.add(new Track(resultset.getInt("id"),resultset.getString("name"),"",
                                    0,"",
                                    0,0
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
    public void updateTrack(int id,int column,String newValue)throws SQLException{
        String columnName=getColumnName(column);
        String query="update tracks set"+columnName+"=? where id=?";
        try(Connection connection=DatabaseConnection.getConnection();
            PreparedStatement statement=connection.prepareStatement(query)){
                if(columnName.equals("name")||columnName.equals("location")||columnName.equals("surface_type"))
                    statement.setString(1,newValue);
                else 
                    statement.setInt(1,Integer.parseInt(newValue));
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
                return "location";
            case 4:
                return "length_of";
            case 5:
                return "surface_type";
            case 6:
                return "turn_count";
            case 7:
                return "elevation_chanfe";
            default:
                return "";
        }
    }
}
