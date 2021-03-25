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
public abstract class Person {
    private String id;
    private String nama;
    private String jenisKelamin;
    private int umur;
    private String kontak;    
    private String alamat;
    
    public Person(String id, String nama, String jenisKelamin, int umur,
                  String kontak, String alamat){
        this.id = id;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.umur = umur;
        this.kontak = kontak;
        this.alamat = alamat;
    }
    
    //SETTER
    public void setId(String id){
        this.id = id;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
    public void setUmur(int umur) {
        this.umur = umur;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public void setKontak(String kontak) {
        this.kontak = kontak;
    }
    
    //GETTER
    public String getId(){
        return id;
    }
    public String getNama() {
        return nama;
    }
    public String getJenisKelamin() {
        return jenisKelamin;
    }
    public int getUmur() {
        return umur;
    }
    public String getAlamat() {
        return alamat;
    }
    public String getKontak() {
        return kontak;
    }   
}
