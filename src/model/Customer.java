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
public class Customer extends Person{
    private List<Pemesanan> listPesanan;
    
    public Customer(String id, String nama, String jenisKelamin, int umur,String kontak, String alamat){
        super(id,nama,jenisKelamin,umur,kontak,alamat);
        listPesanan = new ArrayList<>();
    }
    
    public List<Pemesanan> getListPesanan() {
        return listPesanan;
    }
       
    public void createPemesanan(Pemesanan psn){
        listPesanan.add(psn);        
    }
}
