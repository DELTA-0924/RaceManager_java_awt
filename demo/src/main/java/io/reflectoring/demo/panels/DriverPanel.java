package io.reflectoring.demo.panels;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import io.reflectoring.demo.DataAccess.DAO.DriverDAO;
import io.reflectoring.demo.common.Utility;
import io.reflectoring.demo.contact.DriverTeam;
import io.reflectoring.demo.models.Driver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class DriverPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, ageField, experienceField, skillField, salaryField;
    private JComboBox<String> teamIdField;
    private DriverDAO driverDAO;
    private Utility util=new Utility();
    public DriverPanel() {
        setLayout(new BorderLayout());
        driverDAO=new DriverDAO();
        // Таблица
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Experience", "Team ID", "Skill", "Salary"}, 0);
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

        inputPanel.add(new JLabel("Special Skill:"));
        skillField = new JTextField();
        inputPanel.add(skillField);

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
                    Driver driver = new Driver(
                            0, // ID автогенерируется
                            nameField.getText(),
                            Integer.parseInt(ageField.getText()),
                            Integer.parseInt(experienceField.getText()),
                            util.getidCBX(teamIdField.getSelectedItem().toString()),
                            skillField.getText(),
                            Integer.parseInt(salaryField.getText())
                    );

                     
                    driverDAO.addDriver(driver);

                    // Обновить таблицу
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(DriverPanel.this, "Error: " + ex.getMessage());
                }
            }
        });

            table.getModel().addTableModelListener(e->{
            if(e.getType()==TableModelEvent.UPDATE )
            {
                try{
                int row=e.getFirstRow();
                int col=e.getColumn();
                Object newValue=table.getValueAt(row, col);
                int id=(int)table.getValueAt(row,0);
                
                driverDAO.updateDriver(id, col+1,newValue.toString());
                }catch(SQLException ex ){
                    ex.printStackTrace();
                }
            }
        });

        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) table.getValueAt(selectedRow, 0); // Получаем ID выбранной строки

                // Удаляем запись из базы данных
                driverDAO.deleteFromDatabase(id);

                // Удаляем строку из модели
                tableModel.removeRow(selectedRow);
            }
        });


        JPanel buttonPanel=new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(addButton);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        try {
            
            List<DriverTeam> drivers = driverDAO.getDrivers();

            tableModel.setRowCount(0); // Очистить таблицу
            for (DriverTeam driver : drivers) {
                tableModel.addRow(new Object[]{
                        driver.getId(),
                        driver.getName(),
                        driver.getAge(),
                        driver.getExperience(),
                        driver.getTeamName(),
                        driver.getSpecialSkill(),
                        driver.getSalary()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
