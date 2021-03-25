package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.TempatWisata;
import model.TourGuide;

public class tbModTourGuide extends AbstractTableModel{
    
    List<TourGuide> list = new ArrayList<>();
    private final String[] header = {"Id","Nama","Gender","Umur","Kontak","Alamat"};
    
    
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
        TourGuide tg = list.get(rowIndex);
        switch(columnIndex){
            case 0 : return tg.getId();
            case 1 : return tg.getNama();
            case 2 : return tg.getJenisKelamin();
            case 3 : return tg.getUmur();
            case 4 : return tg.getKontak();
            case 5 : return tg.getAlamat();
            default : return null;
        }
    }
    
    public void addOneRow(TourGuide tg){
        list.add(tg);
        fireTableRowsInserted(list.size()-1, list.size()-1);
    }
    
    public void updateTable(List<TourGuide> listTg){
        list = listTg;
        fireTableDataChanged();
    }
    
}

