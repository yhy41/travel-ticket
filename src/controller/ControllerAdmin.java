/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.*;
import view.GUIAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.print.attribute.Size2DSyntax.MM;
import methodFungsional.dt;

//table model
import tableModel.tbModCustomer;
import tableModel.tbModTempatWisata;
import tableModel.tbModTourGuide;
import tableModel.tbModPaketWisata;
import tableModel.tbModRekapPemesanan;
import view.viewListTG;

import view.viewListTW;
/**
 *
 * @author Lenovo
 */
public class ControllerAdmin extends MouseAdapter{//extends MouseAdapter implements ActionListener {
    private Application model;
    private GUIAdmin viewAdm;
    
    //table view utama
    private tbModTourGuide tbModTG;
    private tbModCustomer tbModCs;
    private tbModTempatWisata tbModTW;
    private tbModPaketWisata tbModPW;
    private tbModRekapPemesanan tbModRP;
    
    private viewListTW viewTW;
    private tbModTempatWisata tbModLsTW;
    private viewListTG viewTG;
    private tbModTourGuide tbModLsTG;
    
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    public ControllerAdmin(Application model, GUIAdmin viewAdm) throws ParseException{
        this.viewAdm = viewAdm;
        this.model = model;

        model.loadAllTourguide();
        model.loadAllTempatWisata();
        model.loadAllCustomer();
        model.loadAllPaketWisata();
        
        //TOUR GUIDE
        viewAdm.addMouseListener(this);
        //table model tour guide
        tbModTG = new tbModTourGuide();
        viewAdm.getTbViewTGTG().setModel(tbModTG);
        
        //table model customer
        tbModCs = new tbModCustomer();
        viewAdm.getTbViewCs().setModel(tbModCs);
        
        //table model tempat wisata
        tbModTW = new tbModTempatWisata();
        viewAdm.getTBViewTW().setModel(tbModTW);
        
        //table model paket wisata
        tbModPW = new tbModPaketWisata();
        viewAdm.getTBViewPW().setModel(tbModPW);
        
        //table model rekap pemesanan
        tbModRP = new tbModRekapPemesanan();
        viewAdm.getTbViewRP().setModel(tbModRP);
        
        //inisialisasi id pada textfield
        viewAdm.getTfIdTG().setText("TG-"+model.getNewIdTG());
        viewAdm.getTfIdInputPW().setText("PW-"+model.getNewIdPW());
        viewAdm.getTfIdInputTW().setText("TW-"+model.getNewIdTW());
        viewAdm.getSlRatingInputTW().setValue(0);


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();       
    //Input TG
        if(source.equals(viewAdm.getBtnInputTG())){
            TourGuide tg = new TourGuide(viewAdm.getTfIdTG().getText(),viewAdm.getTfNamaTG().getText(),viewAdm.getRbJKTG(),Integer.parseInt(viewAdm.getTfUmurTG().getText()),viewAdm.getTfKontakTG().getText(),viewAdm.getTfAlamatTG().getText());
            model.inputTourGuide(tg);
            tbModTG.updateTable(model.getDaftarTG());
            JOptionPane.showMessageDialog(viewAdm, "Data berhasil di input");
            resetViewTG();
        }
    //update table TG
        else if(source.equals(viewAdm.getBtnUpdateTG())){
            tbModTG.updateTable(model.loadAllTourguide());
        }
    //Input TW
        else if(source.equals(viewAdm.getBtnInputTW())){
            TempatWisata tw = new TempatWisata(viewAdm.getTfIdInputTW().getText(),viewAdm.getTfNamaInputTW().getText(),viewAdm.getSlRatingInputTW().getValue(),viewAdm.getTaAlamatInputTW().getText());
            model.inputTempatWisata(tw);
            tbModTW.updateTable(model.getDaftarTW());
            JOptionPane.showMessageDialog(viewAdm, "Data berhasil di input");
            resetViewInputTW();
        }
    //Update Table TW
        else if(source.equals(viewAdm.getBtnUpdateTW())){
            tbModTW.updateTable(model.loadAllTempatWisata());
        }
    //Search Edit TW
        else if(source.equals(viewAdm.getBtnSearchEditTW())){
            searchIdEditTW();
        }
    //Edit TW
        else if(source.equals(viewAdm.getBtnEditTW())){
            TempatWisata tw = model.getTempatWisata(viewAdm.getTfIdSearchEditTW().getText());
            model.editTempatWisata(tw, viewAdm.getTfNamaEditTW().getText(), viewAdm.getTfAlamatEditTW().getText(), viewAdm.getSlRatingEditTW().getValue());
            tbModTW.updateTable(model.getDaftarTW());
            JOptionPane.showMessageDialog(viewAdm, "Data Telah Diubah");
            resetViewEditTW();
        }
    //Search Delete TW
        else if(source.equals(viewAdm.getBtnSearchDeleteTW())){
            searchIdDeleteTW();
        }
    //Delete TW
        else if(source.equals(viewAdm.getBtnDeleteTW())){
            TempatWisata tw = model.getTempatWisata(viewAdm.getTfIdSearchDeleteTW().getText());     
            model.getDaftarTW().remove(model.getDaftarTW().indexOf(tw));
            model.DeleteTempatWisata(tw);
            tbModTW.updateTable(model.getDaftarTW());
            JOptionPane.showMessageDialog(viewAdm, "Data Telah Dihapus");
            resetViewDeleteTW();
        }
    //Input PW
        else if(source.equals(viewAdm.getBtnInputPW())){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            PaketWisata pw = null;
            try {
                pw = new PaketWisata(viewAdm.getTfIdInputPW().getText(),viewAdm.getTfNamaInputPW().getText(),Double.parseDouble(viewAdm.getTfHargaInputPW().getText()),format.parse(dt.ubahPosisi2(viewAdm.getTfTglBInputPW().getText())),format.parse(dt.ubahPosisi2(viewAdm.getTfTglPInputPW().getText())));
            } catch (ParseException ex) {
                Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
            //menambahkan tempat wisata
            List listTWInput = viewAdm.getLsTWInputPW().getSelectedValuesList();
            for(Object nmTW: listTWInput){
                pw.addTempatWisata(model.getTempatWisatabyName(nmTW.toString()));
            }
            //menambahkan tour guide
            boolean isBentrok = false;
            List<String[]> listJTG = new ArrayList<>();
            TourGuide tg;
            List listTGInput = viewAdm.getLsTGInputPW().getSelectedValuesList();
            for(Object nmTG: listTGInput){
                tg = model.getTourGuidebyName(nmTG.toString());
                listJTG = model.getJadwalTG(tg);
                for(String[] row: listJTG){
                    try {
                        if(dt.isBentrok(pw.getTglBerangkat(),pw.getTglPulang(), format.parse(row[0]), format.parse(row[1]))){
                           JOptionPane.showMessageDialog(viewAdm, "jadwal tour guide bentrok : "+tg.getNama());
                           isBentrok = true;
                           return;
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(!isBentrok)
                pw.assignTourGuide(tg);
            }
            
            if(!isBentrok){
                try {
                    model.inputPaketWisata(pw);                
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }

                tbModPW.updateTable(model.getDaftarPW());

                JOptionPane.showMessageDialog(viewAdm, "Data Berhasil Diinput");
                resetViewInputPW();
            }
        }
    //Update Table PW
        else if(source.equals(viewAdm.getBtnRefreshPW())){
            tbModPW.updateTable(model.getDaftarPW());
        }
    //Search Edit PW
        else if(source.equals(viewAdm.getBtnSearchEditPW())){
            searchIdEditPW();
        }
    //Edit PW
        else if(source.equals(viewAdm.getBtnEditPW())){
            PaketWisata pw = model.getPaketWisata(viewAdm.getTfIdSearchEditPW().getText());
            
            pw.getListGuide().clear();
            model.clearListGuide(pw);
            pw.getListTujuan().clear();
            //menambahkan tempat wisata
            List listTWEdit = viewAdm.getLsTWEditPW().getSelectedValuesList();
            for(Object nmTW: listTWEdit){
                pw.addTempatWisata(model.getTempatWisatabyName(nmTW.toString()));
            }
            //menambahkan tour guide
            boolean isBentrok = false;
            List<String[]> listJTG = new ArrayList<>();
            TourGuide tg;
            List<TourGuide> listTGBaru = new ArrayList<>();
            List listTGEdit = viewAdm.getLsTGEditPW().getSelectedValuesList();
            for(Object nmTG: listTGEdit){
                tg = model.getTourGuidebyName(nmTG.toString());
                listJTG = model.getJadwalTG(tg);
                for(String[] row: listJTG){
                    try {
                        System.out.println(pw.getTglBerangkat()+","+pw.getTglPulang()+" - "+ format.parse(row[0])+","+format.parse(row[1]));
                        if(dt.isBentrok(format.parse(dt.ubahPosisi2(viewAdm.getTfTglBEditPW().getText())),format.parse(dt.ubahPosisi2(viewAdm.getTfTglPEditPW().getText())), format.parse(row[0]), format.parse(row[1]))){
                           JOptionPane.showMessageDialog(viewAdm, "jadwal tour guide bentrok : "+tg.getNama());
                           isBentrok = true;
                           return;
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(!isBentrok)
                listTGBaru.add(tg);
            }
            
            System.out.println("jalan bosss");
            pw.setListGuide(listTGBaru);
            
            try {
                model.editPaketWisata(pw,viewAdm.getTfNamaEditPW().getText(),Double.parseDouble(viewAdm.getTfHargaEditPW().getText()),format.parse(dt.ubahPosisi2(viewAdm.getTfTglBEditPW().getText())),format.parse(dt.ubahPosisi2(viewAdm.getTfTglPEditPW().getText())));
            } catch (ParseException ex) {
                Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            tbModPW.updateTable(model.getDaftarPW());

            JOptionPane.showMessageDialog(viewAdm, "Data Berhasil Diedit");
            resetViewEditPW();
        }
    //Search Delete PW
        else if(source.equals(viewAdm.getBtnSearchDeletePW())){
            searchIdDeletePW();
        }
    //Delete PW
        else if(source.equals(viewAdm.getBtnDeletePW())){
            //model.getDaftarPW().remove(model.getDaftarPW().indexOf(model.getPaketWisata(viewAdm.getTfIdSearchDeletePW().getText())));
            PaketWisata pw = model.getPaketWisata(viewAdm.getTfIdSearchDeletePW().getText());
            model.deletePaketWisata(pw);
            tbModPW.updateTable(model.getDaftarPW());
            JOptionPane.showMessageDialog(viewAdm, "Data Telah Dihapus");
            resetViewDeletePW();
        }
    //table cell clicked
        else if(source.equals(viewAdm.getTBViewPW())){
            //cell list tujuan clicked
            if(viewAdm.getTBViewPW().getSelectedColumn() == 3){
                viewTW = new viewListTW();
                tbModLsTW = new tbModTempatWisata();
                tbModLsTW.updateTable(model.loadLsTWPW(model.getDaftarPW().get(viewAdm.getTBViewPW().getSelectedRow()).getId()));
                viewTW.getTbViewTW().setModel(tbModLsTW);
                viewTW.setVisible(true);
            }
            //cell list guide clicked
            else if(viewAdm.getTBViewPW().getSelectedColumn() == 4){
                viewTG = new viewListTG();
                tbModLsTG = new tbModTourGuide();
                tbModLsTG.updateTable(model.loadLsTGPW(model.getDaftarPW().get(viewAdm.getTBViewPW().getSelectedRow()).getId()));
                viewTG.getTbViewTG().setModel(tbModLsTG);
                viewTG.setVisible(true);
            }
        }
    //referesh view cs
        else if(source.equals(viewAdm.getBtnRefreshCs())){
            tbModCs.updateTable(model.getDaftarCs());
        }
    //refresh view rekap pemesanan
        else if(source.equals(viewAdm.getBtnRefreshRP())){
            try{
                List<String[]> listRP = model.getListRP();
                tbModRP.updateTable(listRP);
                System.out.println("berhasil menampilkan rekap pemesanan");
            }catch(SQLException se){
                System.out.println("gagal menampilkan rekap pemesanan");
            }
        }
    
        
    }
    
    public void resetViewTG(){
        viewAdm.getTfIdTG().setText("TG-"+model.getNewIdTG());
        viewAdm.getTfNamaTG().setText("");
        viewAdm.getBgJKTG().clearSelection();
        viewAdm.getTfUmurTG().setText("");
        viewAdm.getTfAlamatTG().setText("");
        viewAdm.getTfKontakTG().setText("");
    }
    public void resetViewInputTW(){
        viewAdm.getTfIdInputTW().setText("TW-"+model.getNewIdTW());
        viewAdm.getTfNamaInputTW().setText("");
        viewAdm.getSlRatingInputTW().setValue(0);
        viewAdm.getTaAlamatInputTW().setText("");
    }  
    public void searchIdEditTW(){        
        TempatWisata tw = model.getTempatWisata(viewAdm.getTfIdSearchEditTW().getText());
        if(tw!=null){
            viewAdm.getTfNamaEditTW().setText(tw.getNama());
            viewAdm.getSlRatingEditTW().setValue(tw.getRating());
            viewAdm.getTfAlamatEditTW().setText(tw.getAlamat());
            JOptionPane.showMessageDialog(viewAdm, "ID Valid");
        }else{
            JOptionPane.showMessageDialog(viewAdm, "ID Tidak Valid");
        
        }
    }
    public void resetViewEditTW(){
        viewAdm.getTfIdSearchEditTW().setText("");
        viewAdm.getTfNamaEditTW().setText("");
        viewAdm.getTfAlamatEditTW().setText("");
        viewAdm.getSlRatingEditTW().setValue(0);
    }      
    public void searchIdDeleteTW(){
        TempatWisata tw = model.getTempatWisata(viewAdm.getTfIdSearchDeleteTW().getText());
        if(tw != null){
            DefaultTableModel tbModel = (DefaultTableModel) viewAdm.getTbViewDeleteTW().getModel();
            tbModel.addRow(new String[]{tw.getId(),tw.getNama(),tw.getAlamat(),Integer.toString(tw.getRating())});
            JOptionPane.showMessageDialog(viewAdm, "ID Valid");
          
        }else{
            JOptionPane.showMessageDialog(viewAdm, "ID Tidak Valid");
        }
    }
    public void resetViewDeleteTW(){
        viewAdm.getTfIdSearchDeleteTW().setText("");
        DefaultTableModel tbModelDel = (DefaultTableModel) viewAdm.getTbViewDeleteTW().getModel();
        tbModelDel.setRowCount(0);
    }      
    public void refreshTWInputPW(){
        String[] listTw = new String[model.getDaftarTW().size()];
        int i = -1;
        for(TempatWisata tw: model.getDaftarTW()){
            i++;
            listTw[i] = tw.getNama();
        }
        viewAdm.getLsTWInputPW().setListData(listTw);
    }
    public void refreshTGinputPW(){
        String[] listTg = new String[model.getDaftarTG().size()];
        int i = -1;
        for(TourGuide tg: model.getDaftarTG()){
            i++;
            listTg[i] = tg.getNama();
        }
        viewAdm.getLsTGInputPW().setListData(listTg);
    }
    public void resetViewInputPW(){
        viewAdm.getTfIdInputPW().setText("PW-"+model.getNewIdPW());
        viewAdm.getTfNamaInputPW().setText("");
        viewAdm.getTfHargaInputPW().setText("");
        viewAdm.getTfTglBInputPW().setText("");
        viewAdm.getTfTglPInputPW().setText("");
        viewAdm.getLsTWInputPW().clearSelection();
        viewAdm.getLsTGInputPW().clearSelection();
    }  
    public void searchIdEditPW(){
        PaketWisata pw = model.getPaketWisata(viewAdm.getTfIdSearchEditPW().getText());
        if(pw != null){
            viewAdm.getTfNamaEditPW().setText(pw.getNama());
            viewAdm.getTfHargaEditPW().setText(Double.toString(pw.getHarga()));
            viewAdm.getTfTglBEditPW().setText(dt.ubahPosisi(format.format(pw.getTglBerangkat())));
            viewAdm.getTfTglPEditPW().setText(dt.ubahPosisi(format.format(pw.getTglPulang())));
            
            //update list tw
            String[] listTw = new String[model.getDaftarTW().size()];
            int itw = -1;
            for(TempatWisata tw: model.getDaftarTW()){
                itw++;
                listTw[itw] = tw.getNama();
            }
            viewAdm.getLsTWEditPW().setListData(listTw);
            //set selected index
            int[] idxSelectTw = new int[pw.getListTujuan().size()];
            int i = 0;
            System.out.println("jumlah list tw "+pw.getListTujuan().size());
            for(TempatWisata tw: pw.getListTujuan()){
                for(int j = 0; j<model.getDaftarTW().size(); j++){
                    System.out.println(tw.getId()+", "+model.getDaftarTW().get(j).getId());
                    if(tw.getId() == model.getDaftarTW().get(j).getId()){
                        System.out.println("jalan");
                        idxSelectTw[i] = j;
                        i++;
                    }
                }
            }
            System.out.println("selected index : "+idxSelectTw);
            viewAdm.getLsTWEditPW().setSelectedIndices(idxSelectTw);
            
            //update list tg
            String[] listTg = new String[model.getDaftarTG().size()];
            int itg = -1;
            for(TourGuide tg: model.getDaftarTG()){
                itg++;
                listTg[itg] = tg.getNama();
            }
            viewAdm.getLsTGEditPW().setListData(listTg);
            //set selected index
            int[] idxSelectTg = new int[pw.getListGuide().size()];
            i = 0;
            for(TourGuide tg: pw.getListGuide()){
                for(int k = 0; k<model.getDaftarTG().size(); k++){
                    if(tg.getId() == model.getDaftarTG().get(k).getId()){
                        idxSelectTg[i] = k;
                        i++;
                    }
                }
            }
            System.out.println("selected index : "+idxSelectTg);
            viewAdm.getLsTGEditPW().setSelectedIndices(idxSelectTg);
            JOptionPane.showMessageDialog(viewAdm, "ID Valid");
        }else{
            JOptionPane.showMessageDialog(viewAdm, "ID Tidak Valid");
        }
    }
    public void resetViewEditPW(){
        viewAdm.getTfIdSearchEditPW().setText("");
        viewAdm.getTfNamaEditPW().setText("");
        viewAdm.getTfHargaEditPW().setText("");
        viewAdm.getTfTglBEditPW().setText("");
        viewAdm.getTfTglPEditPW().setText("");
        viewAdm.getLsTWEditPW().clearSelection();
        viewAdm.getLsTGEditPW().clearSelection();
    }     
    public void searchIdDeletePW(){
        PaketWisata pw = model.getPaketWisata(viewAdm.getTfIdSearchDeletePW().getText());
        if(pw != null){
            DefaultTableModel tbModel = (DefaultTableModel) viewAdm.getTbViewDeletePW().getModel();
            tbModel.addRow(new String[]{pw.getId(),pw.getNama()});
            JOptionPane.showMessageDialog(viewAdm, "ID Valid");
        }else{
            JOptionPane.showMessageDialog(viewAdm, "ID Tidak Valid");
        }
    }
    public void resetViewDeletePW(){
        viewAdm.getTfIdSearchDeletePW().setText("");
        DefaultTableModel tbModelDel = (DefaultTableModel) viewAdm.getTbViewDeletePW().getModel();
        tbModelDel.setRowCount(0);
    }
    
}  
