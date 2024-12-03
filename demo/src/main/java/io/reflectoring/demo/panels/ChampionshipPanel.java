package io.reflectoring.demo.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import io.reflectoring.demo.DataAccess.DAO.ChampionshipsDAO;
import io.reflectoring.demo.models.Championship;

public class ChampionshipPanel extends JPanel{
    private JTable table;
    private DefaultTableModel tableModel;

    ChampionshipsDAO champDAO;
    public ChampionshipPanel(){
        setLayout(new BorderLayout());
        champDAO=new ChampionshipsDAO();
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Season", "Date start", "Date end", "Date end", "position","winner id","prize money"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panelChamp=new JPanel(new GridLayout(6,7));

        JButton btnAddChampionship=new JButton("add championship");                
        JButton btnCalculateWinner=new JButton("calculate championship winner");                

        JTextField cNameField=new JTextField();
        panelChamp.add(new Label("Name:"));
        panelChamp.add(cNameField);

        JTextField cSeasonField=new JTextField();
        panelChamp.add(new Label("Season:"));
        panelChamp.add(cSeasonField);

        JTextField cLocationField=new JTextField();
        panelChamp.add(new Label("Location:"));
        panelChamp.add(cLocationField);

        JTextField cPrizeMoneyField=new JTextField();
        panelChamp.add(new Label("prize money:"));
        panelChamp.add(cPrizeMoneyField);

        JFormattedTextField cStartDate= new JFormattedTextField(new SimpleDateFormat("yyyy-mm-dd"));
        panelChamp.add(new Label("Date start:"));
        panelChamp.add(cStartDate);

        JFormattedTextField cEndDate= new JFormattedTextField(new SimpleDateFormat("yyyy-mm-dd"));
        panelChamp.add(new Label("Date end:"));
        panelChamp.add(cEndDate);
        
          btnAddChampionship.addActionListener(new ActionListener() {                   
            @Override
            public void actionPerformed(ActionEvent e) {                
                try {
                    Championship champ = new Championship(
                        0,
                            cNameField.getText(),Integer.parseInt(cSeasonField.getText()),
                            cStartDate.getText(),cEndDate.getText(),
                            cLocationField.getText(),0,Integer.parseInt( cPrizeMoneyField.getText())
                    );
                    champDAO.addChampionship(champ);
                    JOptionPane.showMessageDialog(ChampionshipPanel.this, "Sponsor added successfully!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(ChampionshipPanel.this, "Error adding sponsor: " + ex.getMessage());
                }
                catch(ParseException ex){
                    ex.printStackTrace();
                }
            }
          });
          btnCalculateWinner.addActionListener(e -> {
            // Открыть диалоговое окно с полем ввода
            String input = JOptionPane.showInputDialog(null, 
                "Введите ID чемпионата:", 
                "Расчет победителя", 
                JOptionPane.PLAIN_MESSAGE);
        
            // Проверяем, что пользователь ввел данные, а не нажал "Отмена"
            if (input != null && !input.trim().isEmpty()) {
                // Логика обработки введенных данных
                
        
                // Пример: вывод сообщения с результатом
                try{
                    champDAO.calculateChampionWinner(Integer.parseInt(input));
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(ChampionshipPanel.this, "Error calculate winner: " + ex.getMessage());
                }

            } else {
                // Сообщение об отмене или отсутствии ввода
                JOptionPane.showMessageDialog(null, 
                    "Ввод отменен или пустой!", 
                    "Ошибка", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        JPanel buttonPanel=new JPanel();
        buttonPanel.add(btnAddChampionship);
        buttonPanel.add(btnCalculateWinner);
        add(scrollPane,BorderLayout.CENTER);
        add(panelChamp,BorderLayout.NORTH);
        add(buttonPanel,BorderLayout.SOUTH);
        refreshTable();
    }
     private void refreshTable() {
        try {
            
            List<Championship> champs = champDAO.getChampionships();

            tableModel.setRowCount(0); // Очистить таблицу
            for (Championship champ: champs) {
                tableModel.addRow(new Object[]{
                    champ.getId(),
                    champ.getName(),
                    champ.getSeason(),
                    champ.getStartDate(),
                    champ.getEndDate(),
                    champ.getLocation(),
                    champ.getWinner(),
                    champ.getPrizeMoney()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}     

