package io.reflectoring.demo.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import io.reflectoring.demo.DataAccess.DAO.TrackDAO;
import io.reflectoring.demo.models.Track;

public class TrackPanel extends JPanel{
    private JTable trackTable;
    private DefaultTableModel tableModel;
    private JTextField  nameField, locationField, lengthField, surfaceField, turnsField, elevationField;
    private JButton addButton;
    private TrackDAO trackDAO;
    public TrackPanel(){
        setLayout(new BorderLayout());
        trackDAO=new TrackDAO();
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Location", "Length", "Surface Type", "Turns", "Elevation"}, 0);
        trackTable = new JTable(tableModel);
        addButton = new JButton("Add Track");

        JScrollPane scrollPane = new JScrollPane(trackTable);

        // Поля ввода
        JPanel inputPanel = new JPanel(new GridLayout(6, 7));        
        nameField = createInputField(inputPanel, "Name:");
        locationField = createInputField(inputPanel, "Location:");
        lengthField = createInputField(inputPanel, "Length:");
        surfaceField = createInputField(inputPanel, "Surface Type:");
        turnsField = createInputField(inputPanel, "Turn Count:");
        elevationField = createInputField(inputPanel, "Elevation Change:");
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Track track = new Track(
                            0,
                            nameField.getText(),
                            locationField.getText(),
                            Integer.parseInt(lengthField.getText()),
                            surfaceField.getText(),
                            Integer.parseInt(turnsField.getText()),
                            Integer.parseInt(elevationField.getText())
                    );
                    trackDAO.addTracks(track);
                    JOptionPane.showInputDialog(TrackPanel.this, "Success: add track");
                    refreshData();
                } catch (Exception ex) {
                    JOptionPane.showInputDialog(TrackPanel.this, "Error: " + ex.getMessage());
                }                      
            }

        });          
        trackTable.getModel().addTableModelListener(e->{
            if(e.getType()==TableModelEvent.UPDATE )
            {
                try{
                int row=e.getFirstRow();
                int col=e.getColumn();
                Object newValue=trackTable.getValueAt(row, col);
                int id=(int)trackTable.getValueAt(row,0);
                
                trackDAO.updateTrack(id, col+1,newValue.toString());
                }catch(SQLException ex ){
                    ex.printStackTrace();
                }
            }
        });      
        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {
            int selectedRow = trackTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) trackTable.getValueAt(selectedRow, 0); // Получаем ID выбранной строки

                // Удаляем запись из базы данных
                trackDAO.deleteFromDatabase(id);

                // Удаляем строку из модели
                tableModel.removeRow(selectedRow);
            }
        });


        JPanel buttonPanel=new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(addButton);        
        
        add(scrollPane,BorderLayout.CENTER);
        add(inputPanel,BorderLayout.NORTH);
        add(buttonPanel,BorderLayout.SOUTH);;
        refreshData();
    }
    private void refreshData() {
        try {
            List<Track> tracks = trackDAO.getTracks();
            tableModel.setRowCount(0); // Очистить таблицу
            for (Track track : tracks) {
                tableModel.addRow(new Object[]{
                        track.getId(),
                        track.getName(),
                        track.getLocation(),
                        track.getLength_of(),
                        track.getSurface_type(),
                        track.getTurn_count(),
                        track.getElevation_change()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showInputDialog(TrackPanel.this, "Error: " + ex.getMessage());
        }
    }
      
    private JTextField createInputField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        panel.add(label);
        panel.add(textField);
        return textField;
    }
}
