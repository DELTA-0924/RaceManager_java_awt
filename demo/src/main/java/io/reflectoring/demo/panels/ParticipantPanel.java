package io.reflectoring.demo.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import io.reflectoring.demo.DataAccess.DAO.RaceParticipantsDAO;
import io.reflectoring.demo.DataAccess.DAO.SponsorDAO;
import io.reflectoring.demo.common.Utility;
import io.reflectoring.demo.contact.RaceParticipantsCotact;
import io.reflectoring.demo.models.RaceParticipants;


public class ParticipantPanel extends JPanel{
    private RaceParticipantsDAO participantDAO=new RaceParticipantsDAO();
    private Utility util=new Utility();    
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> carModelComboBox,driverNameComboBox,navigatorComboBox,raceNameComboBox,teamNameComboBox;
    private JTextField positionField,lapTimeField,penaltiesField,pointsField;
    SponsorDAO sponsorDAO;
    public ParticipantPanel(){
        setLayout(new BorderLayout());
        int teamId=0;
        sponsorDAO=new SponsorDAO();
        tableModel = new DefaultTableModel(new String[]{"ID", "Race name", "Car model", "Driver name","Navigator name","Position","lap time","penalties","points","Team name"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panelChamp=new JPanel(new GridLayout(9,1));
        JButton addTeam=new JButton("add participant");                
        JButton selectTeam=new JButton("select team");
        panelChamp.add(new Label("Position"));
        positionField =new JTextField();
        panelChamp.add(positionField);

        panelChamp.add(new Label("Lap time"));
        lapTimeField=new JTextField("0.0");
        panelChamp.add(lapTimeField);

        panelChamp.add(new Label("Penalties"));
        penaltiesField=new JTextField("0");
        panelChamp.add(penaltiesField);

        panelChamp.add(new Label("Points"));
        pointsField=new JTextField();
        panelChamp.add(pointsField);
        

        panelChamp.add(new Label("race :"));
        raceNameComboBox=util.loadRace();
        panelChamp.add(raceNameComboBox);

        
        
        panelChamp.add(new Label("navigators :"));
        navigatorComboBox=new JComboBox<String>();
        panelChamp.add(navigatorComboBox);

        panelChamp.add(new Label("drivers :"));
        driverNameComboBox=new JComboBox<String>();
        panelChamp.add(driverNameComboBox);

        panelChamp.add(new Label("cars :"));
        carModelComboBox=new JComboBox<String>();
        panelChamp.add(carModelComboBox);
        
        panelChamp.add(selectTeam);
        

        addTeam.addActionListener(new ActionListener() {                        
            @Override
            public void actionPerformed(ActionEvent e) {                
                try {
                    RaceParticipants participant= new RaceParticipants(
                       util.getidCBX(raceNameComboBox.getSelectedItem().toString()),
                       util.getidCBX(carModelComboBox.getSelectedItem().toString()),
                       util.getidCBX(driverNameComboBox.getSelectedItem().toString()),
                       util.getidCBX(navigatorComboBox.getSelectedItem().toString()),
                        Integer.parseInt(positionField.getText()),Double.parseDouble(lapTimeField.getText()),
                        Integer.parseInt(penaltiesField.getText()),0,
                        util.getidCBX(teamNameComboBox.getSelectedItem().toString())
                    );
                    System.out.println( "race id :"+Integer.parseInt( String.valueOf(raceNameComboBox.getSelectedItem().toString().charAt(0))));
                    participantDAO.addParticipants(participant);
                    JOptionPane.showMessageDialog(ParticipantPanel.this, "Participants added successfully!");
                    refreshTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(ParticipantPanel.this, "Error adding participants: " + ex.getMessage());
                }                                                
            }
          });

     

          JButton deleteButton = new JButton("Delete");

          deleteButton.addActionListener(e -> {
              int selectedRow = table.getSelectedRow();
              if (selectedRow != -1) {
                  int Id = (int) table.getValueAt(selectedRow, 0); // Получаем ID выбранной строки
       
                  participantDAO.deleteFromDatabase(Id);
  
                  // Удаляем строку из модели
                  tableModel.removeRow(selectedRow);
              }
          });
  
  
          JPanel buttonPanel=new JPanel();
          buttonPanel.add(deleteButton);
        buttonPanel.add(addTeam);
        
        add(scrollPane,BorderLayout.CENTER);
        add(panelChamp,BorderLayout.NORTH);
        add(buttonPanel,BorderLayout.SOUTH);
        selectTeam.addActionListener(e -> {
            // Создаем выпадающий список с названиями команд
            teamNameComboBox = util.loadTeams();

            // Панель для размещения метки и ComboBox
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Выберите команду:"));
            panel.add(teamNameComboBox);

            // Показываем диалоговое окно с панелью
            int result = JOptionPane.showConfirmDialog(null, panel, 
                    "Выбор команды", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            // Обрабатываем результат
            if (result == JOptionPane.OK_OPTION) {
                String selectedTeam = (String) teamNameComboBox.getSelectedItem();
                if (selectedTeam != null) {
                    int id=Integer.parseInt(String.valueOf(selectedTeam.charAt(0)));                                                          
                    navigatorComboBox.removeAllItems();
                    util.loadNavigators(id).forEach(navigatorComboBox::addItem);

                    driverNameComboBox.removeAllItems();
                    util.loadDrivers(id).forEach(driverNameComboBox::addItem);

                    carModelComboBox.removeAllItems();
                    util.loadCar(id).forEach(carModelComboBox::addItem);
                                        
                    selectTeam.setText("Team :"+selectedTeam);
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Ничего не выбрано", 
                        "Ошибка", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        table.getModel().addTableModelListener(e->{
            if(e.getType()==TableModelEvent.UPDATE)
                {
                    try{                    
                    int row=e.getFirstRow();
                    int col=e.getColumn();
                    Object newValue=table.getValueAt(row, col);
                    int id=(int)table.getValueAt(row,0);;                    
                    participantDAO.updateRaceParticipants(id,col+1,newValue.toString());
                    }
                    catch(SQLException ex){
                        JOptionPane.showMessageDialog(null, 
                        ex.getMessage(), 
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
        });
        refreshTable();
    }
     private void refreshTable() {
        try {
            
            List<RaceParticipantsCotact> participants= participantDAO.getparticipants();

            tableModel.setRowCount(0); // Очистить таблицу
            for (RaceParticipantsCotact participant: participants) {
                tableModel.addRow(new Object[]{
                    participant.getId(),
                    participant.getRaceName(),
                    participant.getCarName(),
                    participant.getDriverName(),
                    participant.getNavigatorName(),
                    participant.getPosistion(),
                    participant.getLap_time(),
                    participant.getPenalties(),
                    participant.getPoints(),
                    participant.getTeamName()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

}
