/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Lenovo
 */
public class Pemesanan {
    private String id;
    private List<PaketWisata> listPaket;
    private double totalHarga;
    private Date tglPesan; //BUAT OBJEK DATE

    public Pemesanan(String id, List<PaketWisata> lsPw, double tHarga, Date tglPesan) {
        this.id = id;
        this.listPaket = lsPw;
        this.totalHarga = tHarga;
        this.tglPesan = tglPesan;
    }
    
    public void addPaketWisata (PaketWisata pw){//DIBUAT DILUAR
        listPaket.add(pw);
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setListPaket(List<PaketWisata> listPaket) {
        this.listPaket = listPaket;
    }
    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }
    public void setTglPesan(Date tglPesan) {
        this.tglPesan = tglPesan;
    }
    public String getId() {
        return id;
    }
    public List<PaketWisata> getListPaket() {
        return listPaket;
    }
    public double getTotalHarga() {
        return totalHarga;
    }
    public Date getTglPesan() {
        return tglPesan;
    }
    
    public void viewPsn(){
        System.out.println("Id Pemesanan : "+id);
        System.out.println("Tanggal Pesan : "+tglPesan);
        System.out.println("");
    }
}
