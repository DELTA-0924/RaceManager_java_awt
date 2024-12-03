package io.reflectoring.demo.panels;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import io.reflectoring.demo.DataAccess.DAO.RaceDAO;
import io.reflectoring.demo.contact.RaceChampTrack;
import io.reflectoring.demo.models.Race;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RacePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, typeField, dateField, trackIdField, championshipIdField, weatherField;

    public RacePanel() {
        setLayout(new BorderLayout());

        // Таблица
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Type", "Date", "Track ID", "Championship ID", "Weather"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Панель ввода
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Type:"));
        typeField = new JTextField();
        inputPanel.add(typeField);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Track ID:"));
        trackIdField = new JTextField();
        inputPanel.add(trackIdField);

        inputPanel.add(new JLabel("Championship ID:"));
        championshipIdField = new JTextField();
        inputPanel.add(championshipIdField);

        inputPanel.add(new JLabel("Weather Conditions:"));
        weatherField = new JTextField();
        inputPanel.add(weatherField);

        // Кнопка добавления
        JButton addButton = new JButton("Add Race");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Race race = new Race(
                            0, // ID автогенерируется в базе
                            nameField.getText(),
                            typeField.getText(),
                            java.sql.Date.valueOf(dateField.getText()),
                            Integer.parseInt(trackIdField.getText()),
                            Integer.parseInt(championshipIdField.getText()),
                            weatherField.getText()
                    );

                    RaceDAO raceDAO = new RaceDAO();
                    raceDAO.addRace(race);

                    // Обновить таблицу
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(RacePanel.this, "Error: " + ex.getMessage());
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
            RaceDAO raceDAO = new RaceDAO();
            List<RaceChampTrack> races = raceDAO.getRaces();

            tableModel.setRowCount(0); // Очистить таблицу
            for (RaceChampTrack race : races) {
                tableModel.addRow(new Object[]{
                        race.getId(),
                        race.getName(),
                        race.getType_of(),
                        race.getDate_of(),
                        race.getTrackName(),
                        race.getChampionshipName(),
                        race.getWeatherCondition()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
