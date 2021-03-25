/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import methodFungsional.dt;
import model.TourGuide;

/**
 *
 * @author Lenovo
 */
public class tbModPenugasanTG extends AbstractTableModel {
    List<String[]> list = new ArrayList<>();
    private final String[] header = {"Paket Wisata","Tanggal Berangkat","Tanggal Pulang","Customer"};
    
    DateFormat format = new SimpleDateFormat("dd-WW-yyyy");
    
    @Override
    public int getRowCount() {
        return list.size();
    }
    
    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] row = list.get(rowIndex);
        switch(columnIndex){
            case 0 : return row[0];
            case 1 : return dt.ubahPosisi(row[1]);
            case 2 : return dt.ubahPosisi(row[2]);
            case 3 : return "Lihat Data";
            default : return null;
        }
    }
    
    public void updateTable(List<String[]> listPngs){
        list = listPngs;
        fireTableDataChanged();
    }
    
}
