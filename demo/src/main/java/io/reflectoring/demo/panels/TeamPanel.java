package io.reflectoring.demo.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


import io.reflectoring.demo.DataAccess.DAO.TeamDAO;
import io.reflectoring.demo.DataAccess.DAO.TeamSponsorDAO;
import io.reflectoring.demo.common.Utility;
import io.reflectoring.demo.models.Team;

public class TeamPanel extends JPanel{
     private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField,countryField;
    private Utility util=new Utility();
    private TeamSponsorDAO teamSponsorDAO=new TeamSponsorDAO();
    TeamDAO teamDAO;
    public TeamPanel(){
        setLayout(new BorderLayout());
        teamDAO=new TeamDAO();
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Country", "Budget"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panelChamp=new JPanel(new GridLayout(4,7));

        JButton addTeam=new JButton("add Team");
        JButton btnaddTeamSponsor=new JButton("add Team sponsor");                                

        nameField=new JTextField();
        panelChamp.add(new Label("Name:"));
        panelChamp.add(nameField);

        countryField=new JTextField();
        panelChamp.add(new Label("Season:"));
        panelChamp.add(countryField);                
        addTeam.addActionListener(new ActionListener() {                        
            @Override
            public void actionPerformed(ActionEvent e) {                
                try {
                    Team team = new Team(
                        0,
                            nameField.getText(),countryField.getText(),0
                    );
                    teamDAO.addTeam(team);
                    JOptionPane.showMessageDialog(TeamPanel.this, "Sponsor added successfully!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(TeamPanel.this, "Error adding sponsor: " + ex.getMessage());
                }                                                
            }
          });
          btnaddTeamSponsor.addActionListener(e->{
            addTeamSponsor();
        });
        JPanel buttonPanel=new JPanel();
        buttonPanel.add(addTeam);
        buttonPanel.add(btnaddTeamSponsor);
        add(scrollPane,BorderLayout.CENTER);
        add(panelChamp,BorderLayout.NORTH);
        add(buttonPanel,BorderLayout.SOUTH);
        refreshTable();
    }
     private void refreshTable() {
        try {
            
            List<Team> teams = teamDAO.getTeams();

            tableModel.setRowCount(0); // Очистить таблицу
            for (Team team: teams) {
                tableModel.addRow(new Object[]{
                    team.getId(),
                    team.getName(),
                    team.getCountry(),
                    team.getBudget()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }


    void addTeamSponsor(){        
        JFrame tsFrame = new JFrame("Manage Teams on Sponsors");
        tsFrame.setSize(600, 400);
        tsFrame.setLayout(new BorderLayout());
        JPanel viewTS = new JPanel(new GridLayout(5, 2));
        JTextField contribution = new JTextField();        
        JButton btnAddTeamSponsors=new JButton("Add ");
        JComboBox<String> comboBoxTeam=util.loadTeams();
        JComboBox<String> comboBoxSponsor=util.loadSponsor();
        btnAddTeamSponsors.addActionListener(e->{
        try{
                String team =comboBoxTeam.getSelectedItem().toString();
                String sponsor= comboBoxSponsor.getSelectedItem().toString();
                String budget=contribution.getText();                             
                teamSponsorDAO.setTeamSponsor(Integer.parseInt(String.valueOf(team.charAt(0))),Integer.parseInt(String.valueOf(sponsor.charAt(0))),Integer.parseInt(budget));
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(tsFrame, "Error fetching teams: " + ex.getMessage());
            }
        });
        viewTS.add(new Label("Teams"));
        viewTS.add(comboBoxTeam);
        viewTS.add(new Label("Sponsors"));
        viewTS.add(comboBoxSponsor);
        viewTS.add(new Label("contribution"));
        viewTS.add(contribution);
        viewTS.add(btnAddTeamSponsors);
        tsFrame.add(viewTS);
        tsFrame.setVisible(true);
    }
}
