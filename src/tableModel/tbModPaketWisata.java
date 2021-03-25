package tableModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import methodFungsional.dt;
import model.PaketWisata;

public class tbModPaketWisata extends AbstractTableModel{
    
    List<PaketWisata> list = new ArrayList<>();
    private final String[] header = {"Id","Nama","Harga","List Tujuan","List Guide","Tanggal Berangkat","Tanggal Pulang"};
    
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
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
        PaketWisata pw = list.get(rowIndex);
        switch(columnIndex){
            case 0 : return pw.getId();
            case 1 : return pw.getNama();
            case 2 : return pw.getHarga();
            case 3 : return "Lihat Data";
            case 4 : return "Lihat Data";    
            case 5 : return dt.ubahPosisi(format.format(pw.getTglBerangkat()));
            case 6 : return dt.ubahPosisi(format.format(pw.getTglPulang()));
            default : return null;
        }
    }
    
    public void updateTable(List<PaketWisata> listPw){
        list = listPw;
        fireTableDataChanged();
    }
    
}

