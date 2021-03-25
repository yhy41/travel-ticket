package model;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import view.GUIAdmin;
public class Database {

    private Application model;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");   
    
    public Database(Application model){
        this.model = model;
    }
    
    private static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private Connection con;
    public void connect(){ 
        try{
            String url="jdbc:mysql://localhost:3306/tubespbo";
            String username = "root";
            String password = "";
            con = DriverManager.getConnection(url, username, password);
        } 
        catch(SQLException se){ 
            System.out.println("Connection error.");
        }  
    }
    
    //TOUR GUIDE
    public void saveTourGuide(TourGuide t){ 
        try{
            String query="insert into tourguide values ('"
                    +t.getId()+"','"
                    +t.getNama()+"','"
                    +t.getJenisKelamin()+"','"
                    +t.getUmur()+"','"
                    +t.getKontak()+"','"
                    +t.getAlamat()+"');";
            Statement s = con.createStatement(); 
            s.execute(query); 
            System.out.println("Saving success.");
        } catch(SQLException se){ 
            System.out.println("Saving error.");
        } 
    }

    public List<String[]> getJadwalTG(TourGuide tg){ 
        try{
            List<String[]> listJTG = new ArrayList<>();
            String[] row;
            String query="select tglBerangkatPW, tglPulangPW \n" +
                        "from paketwisata " +
                        "join tourguidedipaketwisata " +
                        "using (idPW) " +
                        "join tourguide " +
                        "using (idTG) " +
                        "where idTG = '"+tg.getId()+"';";
            Statement s = con.createStatement(); 
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                row = new String[2];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                listJTG.add(row);
            }
            System.out.println("get success.");
            return listJTG;
        } catch(SQLException se){ 
            System.out.println("get error.");
            return null;
        }
    }
    
    public void saveTempatWisata(TempatWisata tw){ 
        try{
            String query = "insert into tempatwisata values ('"
                    +tw.getId()+"','"
                    +tw.getNama()+"','"
                    +tw.getRating( )+"','"
                    +tw.getAlamat()+"');";
            Statement s=con.createStatement();
            s.execute(query);
            
            System.out.println("Saving success.");
        } catch(SQLException se){ 
            System.out.println("Saving error.");
        } 
    }
    public void saveCustomer(Customer c){ 
        try{
            String query="insert into customer values ('"+c.getId()+"','"+c.getNama()+"','"+c.getJenisKelamin()+"','"+c.getUmur( )+"','"+c.getKontak()+"','"+c.getAlamat()+"');";
            Statement s=con.createStatement(); 
            s.execute(query); 
            System.out.println("Saving success.");
        } catch(SQLException se){ 
            System.out.println("Saving error.");
        } 
    }    
    public int getNewIdTWPW() throws SQLException{
        String query = "select count(idTWPW) from tempatwisatadipaketwisata;";
        Statement s = con.createStatement();
        ResultSet rs=s.executeQuery(query);
        int idBaru = 0;
        while(rs.next()){
            idBaru = Integer.parseInt(rs.getString(1));
        }
        return idBaru+1;
    }
    public int getNewIdTGPW() throws SQLException{
        String query = "select count(idTGPW) from tourguidedipaketwisata;";
        Statement s = con.createStatement();
        ResultSet rs=s.executeQuery(query);
        int idBaru = 0;
        while(rs.next()){
            idBaru = Integer.parseInt(rs.getString(1));
        }
        return idBaru+1;
    }   
    public void savePaketWisata(PaketWisata pw) throws SQLException{
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] idTw = new String[pw.getListTujuan().size()];
        for(int i = 0; i< pw.getListTujuan().size(); i++){
            idTw[i] = pw.getListTujuan().get(i).getId();
        }
        String[] idG = new String[pw.getListGuide().size()];
        for(int i = 0; i< pw.getListGuide().size(); i++){
            idG[i] = pw.getListGuide().get(i).getId();
        }
  
        try{
            String query="insert into paketwisata values ('"+pw.getId()+"','"
                    +pw.getNama()+"','"
                    +pw.getHarga( )+"','"
                    +format.format(pw.getTglBerangkat())+"','"
                    +format.format(pw.getTglPulang())+"');";
            Statement s=con.createStatement();           
            s.execute(query);
            //insert ke table tempatwisatadipaketwisata
            for(TempatWisata tw: pw.getListTujuan()){
                query = "insert into tempatwisatadipaketwisata values ('TWPW-"+getNewIdTWPW()+"','"+pw.getId()+"','"+tw.getId()+"');";               
                s.execute(query);              
            }
            //insert ke table tourguidedipaketwisata
            for(TourGuide tg: pw.getListGuide()){
                query = "insert into tourguidedipaketwisata values ('TGPW-"+getNewIdTGPW()+"','"+pw.getId()+"','"+tg.getId()+"');";
                s.execute(query);
            }           
            System.out.println("Saving success.");
        } catch(SQLException se){ 
            System.out.println("Saving error.");
        } 
    }
    
    public void clearListGuide(PaketWisata pw){
        try{
            String query = "delete from tourguidedipaketwisata where idPW = '"+pw.getId()+"';";
            Statement s=con.createStatement(); 
            s.execute(query);
            System.out.println("clear succes");
        }catch(SQLException se){
            System.out.println("clear error");
        }
    }
   
    public List<TourGuide> loadAllTourGuide(){ 
    try{
        List<TourGuide> tourguides = new ArrayList(); 
        String query="select * from tourguide;" ; 
        Statement s=con.createStatement(); 
        ResultSet rs=s.executeQuery(query); TourGuide t;
        while (rs.next()){
            String id=rs.getString(1);
            
            String name=rs.getString(2);
            String jeniskelamin=rs.getString(3);
            int umur = Integer.parseInt(rs.getString(4));
            String kontak = rs.getString(5);
            String alamat = rs.getString(6);
            t = new TourGuide(id,name,jeniskelamin,umur,kontak,alamat);
            tourguides.add(t);
        }
        return tourguides;
    }catch(SQLException se){
        return null; }
    } 
    public List<TempatWisata> loadAllTempatWisata(){ 
    try{
        List<TempatWisata> tempatwisatas = new ArrayList(); 
        String query="select * from tempatwisata;" ; 
        Statement s=con.createStatement(); 
        ResultSet rs=s.executeQuery(query); TempatWisata tw;
        while (rs.next()){
            String id=rs.getString(1);
            
            String name=rs.getString(2);
            String alamat=rs.getString(4);
            int rating = Integer.parseInt(rs.getString(3));
            tw = new TempatWisata(id,name,rating,alamat);
            tempatwisatas.add(tw);
        }
        return tempatwisatas;
    }catch(SQLException se){
        return null; }
    } 
    
    public List<Customer> loadAllCustomer(){ 
    try{
        List<Customer> customers = new ArrayList(); 
        String query="select * from customer;" ; 
        Statement s=con.createStatement(); 
        ResultSet rs=s.executeQuery(query); Customer c;
        while (rs.next()){
            String id=rs.getString(1);
            
            String name=rs.getString(2);
            String jeniskelamin=rs.getString(3);
            int umur = Integer.parseInt(rs.getString(4));
            String kontak = rs.getString(5);
            String alamat = rs.getString(6);
            c = new Customer(id,name,jeniskelamin,umur,kontak,alamat);
            customers.add(c);
        }
        return customers;
    }catch(SQLException se){
        return null; }
    } 
    public List<PaketWisata> loadAllPaketWisata() throws ParseException{ 
    try{
        List<PaketWisata> listPw = new ArrayList(); 
        String query="select * from paketwisata;" ; 
        Statement s=con.createStatement(); 
        ResultSet rs=s.executeQuery(query); PaketWisata pw;
        while (rs.next()){
            String id=rs.getString(1);
            String name=rs.getString(2);
            double harga = Double.parseDouble(rs.getString(3));            
            Date tglBerangkat = format.parse(rs.getString(4));
            Date tglPulang = format.parse(rs.getString(5));
            pw = new PaketWisata(id,name,harga,tglBerangkat,tglPulang);
            listPw.add(pw);
        }
        return listPw;
    }catch(SQLException se){
        return null; }
    } 
    
    public static void deleteRecord() throws SQLException{
  Connection connection = null;
  Statement statement = null;
  int countRecordDeleted = 0;
  String deleteTableQuery = "DELETE FROM tourguide WHERE IdTG = 'TG-7'";
  try {
   connection = getConnection();
   statement = connection.createStatement();
   System.out.println(deleteTableQuery);
   // execute delete SQL stetement
   statement.executeUpdate(deleteTableQuery);
   countRecordDeleted = statement.getUpdateCount();
   System.out.println(countRecordDeleted+" Record berhasil di delete dari TBL_USER!");
  } catch (SQLException ex) {
   System.out.println(ex.getMessage());
  } finally {
   if (statement != null) {
    statement.close();
   }
   if (connection != null) {
    connection.close();
   }
  }
 }

    public void deleteTempatWisata(TempatWisata tw){ 
            try{
                String query="delete from tempatwisata WHERE idTW = ('"+tw.getId()+"');";
                Statement s=con.createStatement(); 
                s.execute(query); 
                System.out.println("delete success.");
            } catch(SQLException se){ 
                System.out.println("delete error.");
            } 
        }
    public void deletePaketWisata(PaketWisata pw){
        try{
            String query = "delete from paketWisata where idPW = ('"+pw.getId()+"');";
            Statement s = con.createStatement();
            s.execute(query);

            query = "delete from tempatWisataDiPaketWisata where idPW = ('"+pw.getId()+"');";
            s = con.createStatement();
            s.execute(query);

            query = "delete from tourGuideDiPaketWisata where idPW = ('"+pw.getId()+"');";
            s = con.createStatement();
            s.execute(query);

            System.out.println("delete success.");
        }catch(SQLException se){ 
            System.out.println("delete error.");
        }
    }

    public void updateTourGuide(TourGuide tg){
        try{              
            String query="update tourguide set namaTG = '"+tg.getNama()
                    +"', jenisKelaminTG = '"+tg.getJenisKelamin()
                    +"', umurTG = '"+tg.getUmur( )
                    +"', kontakTG = '"+tg.getKontak()
                    +"', alamatTG = '"+tg.getAlamat()
                    +"' where idTG = '"+tg.getId()+"';";

            Statement s=con.createStatement(); 
            s.execute(query); 
            System.out.println("update success.");
        } catch(SQLException se){ 
            System.out.println("update error.");
        } 
    }

    public void updatePaketWisata(PaketWisata pw){
        try{               
            String query="update paketWisata set namaPW = '"+pw.getNama()
                    +"', hargaPW = '"+pw.getHarga()
                    +"', tglBerangkatPW = '"+format.format(pw.getTglBerangkat())
                    +"', tglPulangPW = '"+format.format(pw.getTglPulang())
                    +"' where idPW = '"+pw.getId()+"';";
            System.out.println("query : "+query);
            Statement s=con.createStatement(); 
            s.execute(query);

            //menghapus list tujuan lama
            query = "delete from tempatWisataDiPaketWisata where idPW = '"+pw.getId()+"';";
            s.execute(query);
            //query menambahkan list tujuan baru
            for(TempatWisata tw: pw.getListTujuan()){
                query = "insert into tempatwisatadipaketwisata values ('TWPW-"+getNewIdTWPW()+"','"+pw.getId()+"','"+tw.getId()+"');";               
                s.execute(query);              
            }

            //menghapus list guide lama
            query = "delete from tourGuideDiPaketWisata where idPW = '"+pw.getId()+"';";
            s.execute(query);
            //query menambahkan list guide baru
            for(TourGuide tg: pw.getListGuide()){
                query = "insert into tourguidediPaketWisata values ('TWPW-"+getNewIdTGPW()+"','"+pw.getId()+"','"+tg.getId()+"');";               
                s.execute(query);              
            }

            System.out.println("update success.");
        } catch(SQLException se){ 
            System.out.println("update error.");
        }
    }

    public void updateCustomer(Customer c){
        try{           
            String query="update Customer set namaCs = '"+c.getNama()
                    +"', jenisKelaminCs = '"+c.getJenisKelamin()
                    +"', umurCs = '"+c.getUmur()
                    +"', kontakCs = '"+c.getKontak()
                    +"', alamatCs = '"+c.getAlamat()
                    +"' where idCs = '"+c.getId()+"';";

            Statement s=con.createStatement(); 
            s.execute(query); 
            System.out.println("update success.");
        } catch(SQLException se){ 
            System.out.println("update error.");
        } 
    }
    public void updateTempatWisata(TempatWisata tw){
        try{
                String query="update tempatwisata set namaTW = '"+tw.getNama()+"', ratingTW = '"+tw.getRating( )+"', alamatTW = '"+tw.getAlamat()+"' where idTW = '"+tw.getId()+"';";
                Statement s=con.createStatement(); 
                s.execute(query); 
                System.out.println("update success.");
            } catch(SQLException se){ 
                System.out.println("update error.");
            } 
    }

    public List<TempatWisata> loadLsTWPW(String idPW){
        try{
            List<TempatWisata> listTWPW = new ArrayList<>();
            String query = "select idTW from tempatWisata join tempatWisataDiPaketWisata using (idTW) where idPW = '"+idPW+"';";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            String idTw;
            while(rs.next()){
                idTw = rs.getString(1);
                listTWPW.add(model.getTempatWisata(idTw));
            }
            return listTWPW;
        }catch(SQLException se){
            return null;
        }
    }
    public List<TourGuide> loadLsTGPW(String idPW){
        try{
            List<TourGuide> listTGPW = new ArrayList<>();
            String query = "select idTG from tourguide join tourguideDiPaketWisata using (idTG) where idPW = '"+idPW+"';";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            String idTg;
            while(rs.next()){
                idTg = rs.getString(1);
                listTGPW.add(model.getTourGuide(idTg));
            }
            return listTGPW;
        }catch(SQLException se){
            return null;
        }
    }
    
    public List<String[]> searchPenugasan(TourGuide tg){
        try{
            List<String[]> listPngs = new ArrayList<>();
            String[] row;
            String query = "SELECT namaPW, tglBerangkatPW, tglPulangPW "
                    + "from paketwisata "
                    + "join tourguidedipaketwisata "
                    + "using (idPW) "
                    + "join tourguide "
                    + "using (idTG) "
                    + "where idTG = '"+tg.getId()+"';";
            System.out.println("query search penugasan : "+query);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                row= new String[3];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                listPngs.add(row);
            }
            System.out.println("search sukses");
            return listPngs;
        }catch(SQLException se){
            System.out.println("search gagal");
            return null;
        }
    }
    public List<Customer> searchCustomerDiPenugasan(TourGuide tg, PaketWisata pw){
        try{
            List<Customer> listCs = new ArrayList<>();
            String query = "select idCs " +
                            "from customer " +
                            "join pemesanan " +
                            "using (idCs) " +
                            "join paketwisatadipemesanan " +
                            "using (idPsn) " +
                            "join paketwisata " +
                            "using (idPW) " +
                            "join tourguidedipaketwisata " +
                            "using (idPW) " +
                            "join tourguide " +
                            "using (idTG) " +
                            "WHERE idTG = '"+tg.getId()+"'"
                            +"and idPW = '"+pw.getId()+"';";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                listCs.add(model.getCustomer(rs.getString(1)));
            }
            System.out.println("search cs sukses");
            return listCs;
        }catch(SQLException se){
            System.out.println("search cs gagal");
            return null;
        }
    }

    //REKAP PEMESANAN
    public List<String[]> getListRP() throws SQLException{
        try{
            List<String[]> listRP = new ArrayList<>();
            String[] row;
            String query = "select idPsn, namaCs, namaPW, tglPesanPsn " +
                            "from customer " +
                            "join pemesanan " +
                            "using (idCs) " +
                            "join paketwisatadipemesanan " +
                            "using (idPsn) " +
                            "join paketwisata " +
                            "using (idPW);";
            System.out.println("query list rp : "+query);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                row= new String[4];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                listRP.add(row);
            }
            System.out.println("search sukses");
            return listRP;
        }catch(SQLException se){
            System.out.println("search gagal");
            return null;
        }
    }
    
    //PEMESANAN
    public int getNewIdPsn() throws SQLException{
        String query = "select count(idPsn) from pemesanan;";
        Statement s = con.createStatement();
        ResultSet rs=s.executeQuery(query);
        int idBaru = 0;
        while(rs.next()){
            idBaru = Integer.parseInt(rs.getString(1));
        }
        return idBaru+1;
    }
    public int getNewIdPWPsn() throws SQLException{
        String query = "select count(idPWPsn) from paketWisataDiPemesanan;";
        Statement s = con.createStatement();
        ResultSet rs=s.executeQuery(query);
        int idBaru = 0;
        while(rs.next()){
            idBaru = Integer.parseInt(rs.getString(1));
        }
        return idBaru+1;
    }
    public void inputPemesanan(Customer Cs, Pemesanan psn){
        try{
            String query = "insert into pemesanan values ('"
                    +psn.getId()+"','"
                    +psn.getTotalHarga()+"','"
                    +format.format(psn.getTglPesan())+"','"
                    +Cs.getId()+"');";
            Statement s = con.createStatement();
            s.execute(query);
            
            //paket wisata di pemesanan
            for(PaketWisata pw: psn.getListPaket()){
                query = "insert into paketWisataDiPemesanan values ('"
                        +"PWP-"+getNewIdPWPsn()+"','"
                        +psn.getId()+"','"
                        +pw.getId()+"');";
                s.execute(query);
            }
            System.out.println("input berhasil");
        }catch(SQLException se){
            System.out.println("gagal input");
        }
    }
}
    
    

