package io.reflectoring.demo.panels;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.print.attribute.standard.JobPriority;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import io.reflectoring.demo.DataAccess.DAO.CarDAO;
import io.reflectoring.demo.common.Utility;
import io.reflectoring.demo.contact.CarTeam;
import io.reflectoring.demo.models.Car;

public class CarPanel extends JPanel{
    JTable table;
    DefaultTableModel tableModel;
    CarDAO carDAO;
    Utility util;
    public CarPanel(){
        setLayout(new BorderLayout());
        carDAO=new CarDAO();
        util=new Utility();
        tableModel=new DefaultTableModel(new String[]{"ID","Model","Brand","Year","Team Name","Price","Mantenance cost"},0);
        table=new JTable(tableModel);
        JScrollPane scrollPane=new JScrollPane(table);
        JPanel panelCar=new JPanel(new GridLayout(6,1));

        JTextField modelField=new JTextField();
        panelCar.add(new Label("Model:"));
        panelCar.add(modelField);

        JTextField brandField=new JTextField();
        panelCar.add(new Label("Brand: "));
        panelCar.add(brandField);

        JTextField yearField=new JTextField();
        panelCar.add(new Label("Date yyyy-mm-dd"));
        panelCar.add(yearField);


        JTextField priceField=new JTextField();
        panelCar.add(new Label("price :"));
        panelCar.add(priceField);

        JTextField maintenanceCostField=new JTextField();
        panelCar.add(new Label("maintenace cost :"));
        panelCar.add(maintenanceCostField);

        JComboBox<String> teamComboBox=util.loadTeams();
        panelCar.add(new Label("teams "));
        panelCar.add(teamComboBox);
        
        JButton addCar=new JButton("Add car");
        addCar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Car car=new Car(0,modelField.getText(),brandField.getText(),
                    yearField.getText(),util.getidCBX(teamComboBox.getSelectedItem().toString()),
                    Double.parseDouble(priceField.getText()),Double.parseDouble(maintenanceCostField.getText()));
                    carDAO.addCar(car);
                    refreshTable();
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(CarPanel.this, "Error:adding car "+ex.getMessage());
                    ex.printStackTrace();
                }
                catch(ParseException ex){
                    JOptionPane.showMessageDialog(CarPanel.this, "Error:adding car "+ex.getMessage());
                }
                
            }
            

        });
        JPanel buttonPanel=new JPanel();
        buttonPanel.add(addCar);
        add(scrollPane,BorderLayout.CENTER);
        add(panelCar,BorderLayout.NORTH);
        add(buttonPanel,BorderLayout.SOUTH);
        refreshTable();
    }
    void refreshTable(){
        try{
            List<CarTeam> cars=carDAO.getCars();
            tableModel.setRowCount(0);
            for(CarTeam car :cars){
                tableModel.addRow(new Object[]{
                    car.getId(),
                    car.getModel(),
                    car.getBrand(),
                    car.getYear(),
                    car.getTeamName(),
                    car.getPrice(),
                    car.getMaintenance_cost()
                });
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(CarPanel.this, "Error:get car list"+ex.getMessage());
        }
    }
}
