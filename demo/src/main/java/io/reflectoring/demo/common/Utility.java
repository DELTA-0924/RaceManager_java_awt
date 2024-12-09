package io.reflectoring.demo.common;

import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;

import io.reflectoring.demo.DataAccess.DAO.CarDAO;
import io.reflectoring.demo.DataAccess.DAO.ChampionshipsDAO;
import io.reflectoring.demo.DataAccess.DAO.DriverDAO;
import io.reflectoring.demo.DataAccess.DAO.NavigatorDAO;
import io.reflectoring.demo.DataAccess.DAO.RaceDAO;
import io.reflectoring.demo.DataAccess.DAO.SponsorDAO;
import io.reflectoring.demo.DataAccess.DAO.TeamDAO;
import io.reflectoring.demo.DataAccess.DAO.TrackDAO;
import io.reflectoring.demo.contact.CarTeam;
import io.reflectoring.demo.contact.DriverTeam;
import io.reflectoring.demo.contact.NavigatorTeam;
import io.reflectoring.demo.contact.RaceChampTrack;
import io.reflectoring.demo.models.Championship;
import io.reflectoring.demo.models.Sponsor;
import io.reflectoring.demo.models.Team;
import io.reflectoring.demo.models.Track;

public class Utility {
    TeamDAO teamDAO=new TeamDAO();
    SponsorDAO sponsorDAO=new SponsorDAO();
    DriverDAO driverDAO=new DriverDAO();
    NavigatorDAO navigatorDAO=new NavigatorDAO();
    RaceDAO raceDAo=new RaceDAO();
    CarDAO carDAO =new CarDAO();
    TrackDAO trackDAO=new TrackDAO();
    ChampionshipsDAO champDAO=new ChampionshipsDAO();
    public  JComboBox<String> loadTeams() {
        
        JComboBox<String> teamComboBox = new JComboBox<>();
        try {
            java.util.List<Team> teams = teamDAO.getTeams();
            for (Team team : teams) {
                teamComboBox.addItem(team.getId() + " - " + team.getName());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return teamComboBox;
    }
    public  JComboBox<String> loadSponsor() {
        JComboBox<String> teamComboBox = new JComboBox<>();
        try {
            java.util.List<Sponsor> sponsors = sponsorDAO.getSponsors();
            for (Sponsor sponsor : sponsors) {
                teamComboBox.addItem(sponsor.getId() + " - " + sponsor.getName());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return teamComboBox;
    }
    public List<String>loadDrivers(int teamId){
        List<String> driverComboBox = new ArrayList<>();
        try {
            java.util.List<DriverTeam> drivers = driverDAO.getDriversbyTeam(teamId);
            for (DriverTeam driver : drivers) {           
                driverComboBox.add(driver.getId() + " - " + driver.getName());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return driverComboBox;   
    }
    public List<String>loadNavigators(int teamId){
        List<String> navigatorComboBox = new ArrayList<>();
        try {
            java.util.List<NavigatorTeam> navigators = navigatorDAO.getNavigatorsbyTeam(teamId);
            for (NavigatorTeam navigator : navigators) {           
                navigatorComboBox.add(navigator.getId() + " - " + navigator.getName());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return navigatorComboBox;           
    }
    public List<String>loadCar(int teamId){
        List<String> navigatorComboBox = new ArrayList<>();
        try {
            java.util.List<CarTeam> cars = carDAO.getCarsbyTeam(teamId);
            for (CarTeam car : cars) {           
                navigatorComboBox.add(car.getId() + " - " + car.getModel());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return navigatorComboBox;           
    }
    public JComboBox<String>loadRace(){
        JComboBox<String> navigatorComboBox = new JComboBox<>();
        try {
            java.util.List<RaceChampTrack> races = raceDAo.getRacesId();
            for (RaceChampTrack race : races) {           
                navigatorComboBox.addItem(race.getId() + " - " + race.getName());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return navigatorComboBox;           
    }
    public JComboBox<String>loadTracks(){
        JComboBox<String> trackComboBox = new JComboBox<>();
        try {
            java.util.List<Track> tracks = trackDAO.getTracksId();
            for (Track track : tracks) {           
                trackComboBox.addItem(track.getId() + " - " + track.getName());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return trackComboBox;   
    }
    public JComboBox<String>loadChampionships(){
        JComboBox<String> champComboBox = new JComboBox<>();
        try {
            java.util.List<Championship> champs = champDAO.getChampionships();
            for (Championship champ : champs) {           
                champComboBox.addItem(champ.getId() + " - " + champ.getName()+" - "+champ.getStartDate() +" - "+champ.getEndDate());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return champComboBox; 
    }
     public int getidCBX(String str){
        int id= Integer.parseInt(String.valueOf(str.substring(0, str.indexOf("-")-1))); 
        return id;
    }
}
