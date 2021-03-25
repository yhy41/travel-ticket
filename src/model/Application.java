// ini adalah model
package model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import tableModel.tbModTourGuide;
/**
 *
 * @author Lenovo
 */
// untuk admin
public class Application {
    private List<Customer> daftarCustomer;
    private List<TourGuide> daftarTourGuide;
    private List<PaketWisata> daftarPaketWisata;
    private List<TempatWisata> daftarTempatWisata;
    
    Database db;
    
    public Application(){
        daftarCustomer = new ArrayList<>();
        daftarTourGuide = new ArrayList<>();
        daftarPaketWisata = new ArrayList<>();
        daftarTempatWisata = new ArrayList<>();
        
        db = new Database(this); 
        db.connect();
    }
    
    //TOUR GUIDE
    public void inputTourGuide(TourGuide tg){
        daftarTourGuide.add(tg);
        db.saveTourGuide(tg);        
    }
    
    public List<String[]> getJadwalTG(TourGuide tg){
        return db.getJadwalTG(tg);
    }
    
    public void clearListGuide(PaketWisata pw){
        db.clearListGuide(pw);
    }

    public List<TourGuide> loadAllTourguide(){ 
        daftarTourGuide=db.loadAllTourGuide();
        return daftarTourGuide;
    } 
    
    public void editTourGuide(TourGuide tg, String nama, String jk, int umur, String kontak, String alamat){
        tg.setNama(nama);
        tg.setJenisKelamin(jk);
        tg.setUmur(umur);
        tg.setAlamat(alamat);
        tg.setKontak(kontak);
        db.updateTourGuide(tg);
    }   
    public TourGuide getTourGuide(String id){
        System.out.println("jalan");
        TourGuide tg = null;
        
        for(TourGuide t: daftarTourGuide){
            System.out.println(t.getId()+", "+id);
            if(t.getId().equals(id)){
                System.out.println("ketemu bos");
                tg = t;
                break;
            }
        }
        return tg;
    }
    public TourGuide getTourGuidebyName(String name){
        TourGuide tg = null;
        
        for(TourGuide t: daftarTourGuide){
            if(t.getNama() == name){
                tg = t;
                break;
            }
        }
        return tg;
    }
    public List<TourGuide> getDaftarTG(){
        return daftarTourGuide;  
    }
    public List<String[]> searchPenugasan(TourGuide tg){
        return db.searchPenugasan(tg);
    }
    public List<Customer> searchCustomerDiPenugasan(TourGuide tg,PaketWisata pw){
        return db.searchCustomerDiPenugasan(tg,pw);
    }
    


    //CUSTOMER
    public List<Customer> getDaftarCs(){
        return daftarCustomer;
        
    }
    public void inputCustomer(Customer c){
        daftarCustomer.add(c);
        db.saveCustomer(c);
        
    }
    public void loadAllCustomer(){ 
        daftarCustomer=db.loadAllCustomer();
    }
    public void editCustomer(Customer cs, String nama,String jk, int umur, String kontak, String alamat){
        cs.setNama(nama);
        cs.setJenisKelamin(jk);
        cs.setUmur(umur);
        cs.setAlamat(alamat);
        cs.setKontak(kontak);     
        db.updateCustomer(cs);
    }
    public Customer getCustomer(String id){
        Customer cs = null;
        
        for(Customer c: daftarCustomer){
            if(c.getId().equals(id)){
                cs = c;
                break;
            }
        }
        return cs;
    }
    
    
    //REKAP PEMESANAN
    public List<String[]> getListRP() throws SQLException{
        return db.getListRP();
    }
    
    
    //PAKET WISATA
    public List<PaketWisata> getDaftarPW(){
        return daftarPaketWisata;
    }
    public void loadAllPaketWisata() throws ParseException{
        daftarPaketWisata = db.loadAllPaketWisata();
    }
    public void inputPaketWisata(PaketWisata pw) throws SQLException{
        daftarPaketWisata.add(pw);
        db.savePaketWisata(pw);
    }
    public void deletePaketWisata(PaketWisata pw){
        daftarPaketWisata.remove(pw);
        db.deletePaketWisata(pw);
    }
    public void viewPaketWisata(){
        for(PaketWisata pw: daftarPaketWisata){
            pw.viewPaketWisata();
        }
    }
    public PaketWisata getPaketWisata(String id){
        PaketWisata pw = null;
        for(PaketWisata w: daftarPaketWisata){
            if(w.getId().equals(id)){
                pw = w;
                break;
            }
        }
        return pw;
    }
    public PaketWisata getPaketWisatabyName(String name){
        PaketWisata pw = null;
        for(PaketWisata w: daftarPaketWisata){
            if(w.getNama().equals(name)){
                pw = w;
                break;
            }
        }
        return pw;
    }
    public void editPaketWisata(PaketWisata pw, String nama, double harga,Date tglB, Date tglP){  
        pw.setNama(nama);
        pw.setHarga(harga);
        pw.setTglBerangkat(tglB);
        pw.setTglPulang(tglP);
        db.updatePaketWisata(pw);
    }
    
    
    //TEMPAT WISATA
    public List<TempatWisata> getDaftarTW(){
        return daftarTempatWisata;
    }
    
    public List<TempatWisata> loadAllTempatWisata(){ 
        daftarTempatWisata=db.loadAllTempatWisata();
        return daftarTempatWisata;
    }
    
    public void DeleteTempatWisata(TempatWisata tw){
        db.deleteTempatWisata(tw);
    }
    public void inputTempatWisata(TempatWisata tw){
        daftarTempatWisata.add(tw);
        db.saveTempatWisata(tw);
    }
    public void viewTempatWisata(){
        for(TempatWisata pw: daftarTempatWisata){
            System.out.println("Id : "+pw.getId());
            System.out.println("Nama : "+pw.getNama());
            System.out.println("Alamat : "+pw.getAlamat());
            System.out.println("Rating : "+pw.getRating());
            System.out.println("");
        }
    }
    public TempatWisata getTempatWisata(String id){
        TempatWisata tw = null;
        for(TempatWisata t: daftarTempatWisata){
            if(t.getId().equals(id)){
                tw = t;
                break;
            }
        }
        return tw;
    }
    public TempatWisata getTempatWisatabyName(String name){
        TempatWisata tw = null;
        for(TempatWisata t: daftarTempatWisata){
            if(t.getNama().equals(name)){
                tw = t;
                break;
            }
        }
        return tw;
    }
    public void editTempatWisata(TempatWisata tw, String nama, String alamat, int rating){       
        tw.setNama(nama);
        tw.setAlamat(alamat);
        tw.setRating(rating);
        db.updateTempatWisata(tw);
    }
    //coba yahya 
     public String searchTourguide (String id) {
        int i = 0;
        while ((i < daftarTourGuide.size()) && (daftarTourGuide.get(i).getId()!= id)) {            
            i++;
        }
        return null;
        //return db.loadOneTourGuideById(id).toString();
        }
     public String[] getTourguideListId() {
        String[] listId = new String[daftarTourGuide.size()];
        for (int i = 0; i < daftarTourGuide.size(); i++) {
            listId[i] = daftarTourGuide.get(i).getId();
        }
        return listId;
    }
    
    //Mendapatkan id baru
    public int getNewIdCs(){
        if (daftarCustomer.size()==0){ 
            return 1;
        }else{
            String lastId = daftarCustomer.get(daftarCustomer.size()-1).getId(); 
            String lastNumId = lastId.substring(2);
            int lastNoId = Integer.parseInt(lastNumId); 
            return lastNoId+1;
        }
    }   
    public int getNewIdTG(){
        if (daftarTourGuide.size()==0){
            return 1;
        }else{
            String lastId=daftarTourGuide.get(daftarTourGuide.size()-1).getId(); 
            String lastNumId=lastId.substring(3);
            int lastNoId = Integer.parseInt(lastNumId); 
            return lastNoId+1;
        } 
    }
    public int getNewIdTW(){
        if (daftarTempatWisata.size()==0) return 1; 
        else{
            String lastId=daftarTempatWisata.get(daftarTempatWisata.size()-1).getId(); 
            String lastNumId=lastId.substring(3);
            int lastNoId = Integer.parseInt(lastNumId); 
            return lastNoId+1;
        } 
    }    
    public int getNewIdPW(){
        if (daftarPaketWisata.size()==0) return 1; 
        else{
            String lastId=daftarPaketWisata.get(daftarPaketWisata.size()-1).getId(); 
            String lastNumId=lastId.substring(3);
            int lastNoId = Integer.parseInt(lastNumId); 
            return lastNoId+1;
        } 
    }
    
    
    
    
    //PEMESANAN
    public int getNewIdPsn() throws SQLException{
        return db.getNewIdPsn();
    }
    public void createPemesananCs(Customer Cs, String id, List<PaketWisata> lsPw, double tHarga, Date tglPesan){
        Pemesanan psn = new Pemesanan(id, lsPw, tHarga, tglPesan);
        Cs.createPemesanan(psn);
        db.inputPemesanan(Cs,psn);
    }
    
    //untuk list tempat wisata didalam table paket wisata
    public List<TempatWisata> loadLsTWPW(String idPW){
        return db.loadLsTWPW(idPW);
    }
    //untuk list tour guide di dalam table paket wisata
    public List<TourGuide> loadLsTGPW(String idPW){
        return db.loadLsTGPW(idPW);
    }

  
}

    
    

