/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Lenovo
 */
public class TempatWisata {
    private String id;
    private static int sidTW = 1;
    private String nama;
    private String alamat;
    private int rating;
    
    public TempatWisata(String id, String nama,int rating, String alamat){
        this.id = id;
        this.nama = nama;
        this.rating = rating;
        this.alamat = alamat;
    }
    
    public TempatWisata(String id,String nama, String alamat, int rating){
        this.id = "TW-"+sidTW;
        this.nama = nama;
        this.alamat = alamat;
        this.rating = rating;
        sidTW++;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }
    
    public int getRating() {
        return rating;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    public void setRating(int rating){
        this.rating = rating;
    }
    
       
}
