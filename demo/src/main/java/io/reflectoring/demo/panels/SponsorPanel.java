package io.reflectoring.demo.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import io.reflectoring.demo.DataAccess.DAO.SponsorDAO;

import io.reflectoring.demo.models.Sponsor;


public class SponsorPanel extends JPanel{
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField,industryField,budgetField;
    SponsorDAO sponsorDAO;
    public SponsorPanel(){
        setLayout(new BorderLayout());
        sponsorDAO=new SponsorDAO();
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Industry", "Budget"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panelChamp=new JPanel(new GridLayout(4,7));

        JButton addTeam=new JButton("add Team");                

        nameField=new JTextField();
        panelChamp.add(new Label("Name:"));
        panelChamp.add(nameField);

        industryField=new JTextField();
        panelChamp.add(new Label("type industry:"));
        panelChamp.add(industryField);                

        budgetField=new JTextField();
        panelChamp.add(new Label("budget:"));
        panelChamp.add(budgetField); 

        addTeam.addActionListener(new ActionListener() {                        
            @Override
            public void actionPerformed(ActionEvent e) {                
                try {
                    Sponsor sponsor= new Sponsor(
                        0,
                            nameField.getText(),industryField.getText(),Double.parseDouble(budgetField.getText())
                    );
                    sponsorDAO.addSponsor(sponsor);
                    JOptionPane.showMessageDialog(SponsorPanel.this, "Sponsor added successfully!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(SponsorPanel.this, "Error adding sponsor: " + ex.getMessage());
                }                                                
            }
          });
        JPanel buttonPanel=new JPanel();
        buttonPanel.add(addTeam);
        add(scrollPane,BorderLayout.CENTER);
        add(panelChamp,BorderLayout.NORTH);
        add(buttonPanel,BorderLayout.SOUTH);
        refreshTable();
    }
     private void refreshTable() {
        try {
            
            List<Sponsor> sponsors= sponsorDAO.getSponsors();

            tableModel.setRowCount(0); // Очистить таблицу
            for (Sponsor team: sponsors) {
                tableModel.addRow(new Object[]{
                    team.getId(),
                    team.getName(),
                    team.getIndustry(),
                    team.getBudget()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
