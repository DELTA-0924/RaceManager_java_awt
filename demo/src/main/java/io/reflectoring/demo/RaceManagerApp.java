package io.reflectoring.demo;

import javax.swing.*;

import io.reflectoring.demo.panels.CarPanel;
import io.reflectoring.demo.panels.ChampionshipPanel;
import io.reflectoring.demo.panels.DriverPanel;
import io.reflectoring.demo.panels.NavigatorPanel;
import io.reflectoring.demo.panels.ParticipantPanel;
import io.reflectoring.demo.panels.RacePanel;
import io.reflectoring.demo.panels.SponsorPanel;
import io.reflectoring.demo.panels.TeamPanel;
import io.reflectoring.demo.panels.TrackPanel;

import java.awt.*;


public class RaceManagerApp {
    private JFrame frame;
    public RaceManagerApp() {    
        frame = new JFrame("Race Manager");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();                                
        tabbedPane.addTab("Championships", new ChampionshipPanel());
        tabbedPane.addTab("Track", new TrackPanel());   
        tabbedPane.addTab("Races", new RacePanel());
        tabbedPane.addTab("Sponsor", new SponsorPanel());   
        tabbedPane.addTab("Team", new TeamPanel());                              
        tabbedPane.addTab("Cars", new CarPanel());   
        tabbedPane.addTab("Drivers", new DriverPanel());
        tabbedPane.addTab("Navigators", new NavigatorPanel());                              
        tabbedPane.addTab("Participants", new ParticipantPanel());   
        frame.add(tabbedPane);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RaceManagerApp::new);
    }
}
;