package io.reflectoring.demo.panels;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import io.reflectoring.demo.DataAccess.DAO.NavigatorDAO;
import io.reflectoring.demo.common.Utility;

import io.reflectoring.demo.contact.NavigatorTeam;

import io.reflectoring.demo.models.Navigator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NavigatorPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, ageField, experienceField, salaryField;
    private JComboBox<String> teamIdField;
    private NavigatorDAO navigatorDAO;
    private Utility util=new Utility();
    public NavigatorPanel() {
        setLayout(new BorderLayout());
        navigatorDAO=new NavigatorDAO();
        // Таблица
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Team ID", "Skill", "Salary"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Панель ввода
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        inputPanel.add(new JLabel("Experience (years):"));
        experienceField = new JTextField();
        inputPanel.add(experienceField);

        inputPanel.add(new JLabel("Team ID:"));
        teamIdField =util.loadTeams();
        inputPanel.add(teamIdField);

        inputPanel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        inputPanel.add(salaryField);

        // Кнопка добавления
        JButton addButton = new JButton("Add Driver");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String team =teamIdField.getSelectedItem().toString();
                    Navigator navigator = new Navigator(
                            0, // ID автогенерируется
                            nameField.getText(),
                            Integer.parseInt(ageField.getText()),
                            Integer.parseInt(experienceField.getText()),
                            util.getidCBX(team),                            
                            Integer.parseInt(salaryField.getText())
                    );

                     
                    navigatorDAO.addNavigator(navigator);

                    // Обновить таблицу
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(NavigatorPanel.this, "Error: " + ex.getMessage());
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        try {
            
            List<NavigatorTeam> navigators = navigatorDAO.getNavigators();

            tableModel.setRowCount(0); // Очистить таблицу
            for (NavigatorTeam navigator : navigators) {
                tableModel.addRow(new Object[]{
                    navigator.getId(),
                    navigator.getName(),
                    navigator.getAge(),
                    navigator.getExperience(),
                    navigator.getTeamName(),                    
                    navigator.getSalary()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
