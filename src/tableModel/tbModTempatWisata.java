package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.TempatWisata;

public class tbModTempatWisata extends AbstractTableModel{
    
    List<TempatWisata> list = new ArrayList<>();
    private final String[] header = {"Id","Nama","Rating","Alamat"};
    
    
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
        TempatWisata tw = list.get(rowIndex);
        switch(columnIndex){
            case 0 : return tw.getId();
            case 1 : return tw.getNama();
            case 2 : return tw.getRating();
            case 3 : return tw.getAlamat();
            default : return null;
        }
    }
    
    public void addOneRow(TempatWisata tw){
        list.add(tw);
        fireTableDataChanged();
    }
    
    public void updateTable(List<TempatWisata> listTw){
        list = listTw;
        fireTableDataChanged();
    }
    
}

