package model;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class PaketWisata {
    private String id;
    private String nama;
    private double harga;
    private List<TourGuide> listGuide;
    private List<TempatWisata> listTujuan;
    private Date tglBerangkat, tglPulang;  
    
     public PaketWisata(String id,String nama, double harga, Date tglBerangkat, Date tglPulang){
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        listGuide = new ArrayList<>();
        listTujuan = new ArrayList<>();
        this.tglBerangkat = tglBerangkat;
        this.tglPulang = tglPulang;
    }
       
    public void setId(String id) {
        this.id = id;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setHarga(double harga) {
        this.harga = harga;
    }
    public void setListGuide(List<TourGuide> listGuide) {
        this.listGuide = listGuide;
    }
    public void setListTujuan(List<TempatWisata> listTujuan) {
        this.listTujuan = listTujuan;
    }
    public void setTglBerangkat(Date tglBerangkat) {
        this.tglBerangkat = tglBerangkat;
    }
    public void setTglPulang(Date tglPulang) {
        this.tglPulang = tglPulang;
    }    

    public String getId(){
        return id;
    }
    public String getNama() {
        return nama;
    }
    public double getHarga() {
        return harga;
    }
    public List<TourGuide> getListGuide() {
        return listGuide;
    }
    public List<TempatWisata> getListTujuan() {
        return listTujuan;
    }
    public Date getTglBerangkat() {
        return tglBerangkat;
    }
    public Date getTglPulang() {
        return tglPulang;
    }
   
    public void addTempatWisata(TempatWisata wisata){
        listTujuan.add(wisata);
    }    
    public void assignTourGuide(TourGuide guide){
        listGuide.add(guide);
    }
    
    public void viewPaketWisata(){
        System.out.println("Id : "+id);
        System.out.println("Nama : "+nama);
        System.out.println("Harga : "+harga);
        System.out.println("List Tour Guide : ");
            for(TourGuide tg: listGuide){
                System.out.println("    Nama : "+tg.getNama());
            }
        System.out.println("List Tempat Wisata : ");
            for(TempatWisata tw: listTujuan){
                System.out.println("    Nama : "+tw.getNama());
            }
        System.out.println("Tanggal Berangkat : "+tglBerangkat);
        System.out.println("Tanggal Pulang : "+tglPulang);
        System.out.println("");
    }

    
}
