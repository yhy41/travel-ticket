package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.TempatWisata;
import model.Customer;

public class tbModCustomer extends AbstractTableModel{
    
    List<Customer> list = new ArrayList<>();
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
        Customer cs = list.get(rowIndex);
        switch(columnIndex){
            case 0 : return cs.getId();
            case 1 : return cs.getNama();
            case 2 : return cs.getJenisKelamin();
            case 3 : return cs.getUmur();
            case 4 : return cs.getKontak();
            case 5 : return cs.getAlamat();
            default : return null;
        }
    }
    
    public void updateTable(List<Customer> listCs){
        list = listCs;
        fireTableDataChanged();
    }
    
}

