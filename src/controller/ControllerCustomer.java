/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Application;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import methodFungsional.dt;
import model.Customer;
import model.PaketWisata;
import view.GUICustomer;
import model.Pemesanan;
import model.TempatWisata;
import model.TourGuide;
import tableModel.tbModCustomer;
import tableModel.tbModPaketWisata;
import tableModel.tbModTempatWisata;
import tableModel.tbModTourGuide;
import view.viewListTG;
import view.viewListTW;

/**
 *
 * @author Lenovo
 */
public class ControllerCustomer extends MouseAdapter{
    private GUICustomer viewCs;
    private Application model;
    
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    //table model
    private tbModPaketWisata tbModPW;
    private tbModTourGuide tbModTG;
    private tbModTempatWisata tbModTW;
    
    private viewListTW viewTW;
    private tbModTempatWisata tbModLsTW;
    private viewListTG viewTG;
    private tbModTourGuide tbModLsTG;
    
    public ControllerCustomer(Application model, GUICustomer viewCs) throws SQLException{
        this.viewCs = viewCs;
        this.model = model;
        
        //load customer data
        model.loadAllCustomer();
        
        //set table model
        tbModPW = new tbModPaketWisata();
        viewCs.getTbLPW().setModel(tbModPW);
        tbModTG = new tbModTourGuide();
        viewCs.getTbLTG().setModel(tbModTG);
        tbModTW = new tbModTempatWisata();
        viewCs.getTbLTW().setModel(tbModTW);
        
        //add listener
        viewCs.addMouseListener(this);
        
        //set id
        viewCs.getTfIdReg().setText("C-"+model.getNewIdCs());
        viewCs.getTfIdPsnPsn().setText("P-"+model.getNewIdPsn());
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
    //Registrasi Customer
        if(source.equals(viewCs.getBtnRegistrasiReg())){
            model.inputCustomer(new Customer(viewCs.getTfIdReg().getText(), viewCs.getTfNamaReg().getText(),viewCs.getJKReg(),Integer.parseInt(viewCs.getTfUmurReg().getText()),viewCs.getTfKontakReg().getText(),viewCs.getTaAlamatReg().getText()));
            JOptionPane.showMessageDialog(viewCs, "Data Berhasil Ditambahkan!");
            resetViewReg();
        }
    //search id pemesanan
        else if(source.equals(viewCs.getBtnSearchIdCsPsn())){
            Customer cs = model.getCustomer(viewCs.getTfIdCsPsn().getText());
            if(cs != null){                
                //set isi list paket wisata
                String[] listPw = new String[model.getDaftarPW().size()];
                int i = -1;
                for(PaketWisata pw: model.getDaftarPW()){
                    i++;
                    listPw[i] = pw.getNama();
                }
                viewCs.getLsPaketWisataPsn().setListData(listPw);
                
                JOptionPane.showMessageDialog(viewCs, "Id Valid");
            }else{
                JOptionPane.showMessageDialog(viewCs, "Id Tidak Valid");                
            }
        }
    //refresh total harga
        else if(source.equals(viewCs.getBtnGetTHargaPsn())){
            double th = 0;    
            List listPWPsn = viewCs.getLsPaketWisataPsn().getSelectedValuesList();
            for(Object nmPW: listPWPsn){
                th += model.getPaketWisatabyName(nmPW.toString()).getHarga();
            }
            viewCs.getTfTotalHargaPsn().setText(Double.toString(th));
        }
    //input pemesanan
        else if(source.equals(viewCs.getBtnPesanPsn())){
            Customer cs = model.getCustomer(viewCs.getTfIdCsPsn().getText());
            if(cs != null){
                List<PaketWisata> listPWPsn = new ArrayList<>();
                List listPWNmPsn = viewCs.getLsPaketWisataPsn().getSelectedValuesList();      
                double tHarga = Double.parseDouble(viewCs.getTfTotalHargaPsn().getText());
                for(Object nmPW: listPWNmPsn){
                    listPWPsn.add(model.getPaketWisatabyName(nmPW.toString()));
                }
                
                try {
                    model.createPemesananCs(cs,viewCs.getTfIdPsnPsn().getText(),listPWPsn, tHarga, format.parse(dt.ubahPosisi2(viewCs.getTfTglPesanPsn().getText())));
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerCustomer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                JOptionPane.showMessageDialog(viewCs, "Data Berhasil Ditambahkan!");
                try {
                    resetViewPsn();
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerCustomer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(viewCs, "Id Customer Tidak Tersedia!");

            }
        }
    //cari id cs udp
        else if(source.equals(viewCs.getBtnCariIdCsUDP())){
            String idCs = viewCs.getTfIdCsUDP().getText();
            Customer cs = model.getCustomer(idCs);
            if(cs != null){
                viewCs.getTfNamaUDP().setText(cs.getNama());
                if(cs.getJenisKelamin() == "Laki-Laki"){
                    viewCs.getRbLakiUDP().setSelected(true);
                }else{
                    viewCs.getRbPerempuanUDP().setSelected(true);
                }
                viewCs.getTfUmurUDP().setText(String.valueOf(cs.getUmur()));
                viewCs.getTaAlamatUDP().setText(cs.getAlamat());
                viewCs.getTfKontakUDP().setText(cs.getKontak());
                JOptionPane.showMessageDialog(viewCs, "Id Valid");
            }else{
                JOptionPane.showMessageDialog(viewCs, "Id Tidak Valid");
            }
        }
    //ubah data pribadi customer
        else if(source.equals(viewCs.getBtnUpdateUDP())){
            Customer cs = model.getCustomer(viewCs.getTfIdCsUDP().getText());
            String jK;
            if(viewCs.getRbLakiUDP().isSelected()){
                jK = "Laki-Laki";
            }else{
                jK = "Perempuan";
            }
            model.editCustomer(cs, viewCs.getTfNamaUDP().getText(), jK, Integer.parseInt(viewCs.getTfUmurUDP().getText()), viewCs.getTaAlamatUDP().getText(), viewCs.getTfKontakUDP().getText());
            JOptionPane.showMessageDialog(viewCs, "Data Diupdate");
            resetViewUDP();
        }
    //lihat paket wisata
        else if(source.equals(viewCs.getBtnRefreshLPW())){
            tbModPW.updateTable(model.getDaftarPW());
        }
    //lihat tour guide
        else if(source.equals(viewCs.getBtnRefreshLTG())){
            tbModTG.updateTable(model.getDaftarTG());
        }
    //lihat tempat wisata
        else if(source.equals(viewCs.getBtnRefreshLTW())){
            tbModTW.updateTable(model.getDaftarTW());
        }
    //table cell clicked
        else if(source.equals(viewCs.getTbLPW())){
            //cell list tujuan clicked
            if(viewCs.getTbLPW().getSelectedColumn() == 3){
                viewTW = new viewListTW();
                tbModLsTW = new tbModTempatWisata();
                tbModLsTW.updateTable(model.loadLsTWPW(model.getDaftarPW().get(viewCs.getTbLPW().getSelectedRow()).getId()));
                viewTW.getTbViewTW().setModel(tbModLsTW);
                viewTW.setVisible(true);
            }
            //cell list guide clicked
            else if(viewCs.getTbLPW().getSelectedColumn() == 4){
                viewTG = new viewListTG();
                tbModLsTG = new tbModTourGuide();
                tbModLsTG.updateTable(model.loadLsTGPW(model.getDaftarPW().get(viewCs.getTbLPW().getSelectedRow()).getId()));
                viewTG.getTbViewTG().setModel(tbModLsTG);
                viewTG.setVisible(true);
            }
        }
    }
    
    public void resetViewReg(){
        viewCs.getTfIdReg().setText("C-"+model.getNewIdCs());
        viewCs.getTfNamaReg().setText("");
        viewCs.getBgJKReg().clearSelection();
        viewCs.getTfUmurReg().setText("");
        viewCs.getTfKontakReg().setText("");
        viewCs.getTaAlamatReg().setText("");
    }
    
    public void resetViewPsn() throws SQLException{
        viewCs.getTfIdCsPsn().setText("");
        viewCs.getTfIdPsnPsn().setText("P-"+model.getNewIdPsn());
        viewCs.getLsPaketWisataPsn().clearSelection();
        viewCs.getTfTglPesanPsn().setText("");
        viewCs.getTfTotalHargaPsn().setText("");
    }  
    
    public void resetViewUDP(){
        viewCs.getTfIdCsUDP().setText("");
        viewCs.getTfNamaUDP().setText("");
        viewCs.getBgJKUDP().clearSelection();
        viewCs.getTfUmurUDP().setText("");
        viewCs.getTfKontakUDP().setText("");
        viewCs.getTaAlamatUDP().setText("");
    }
}
