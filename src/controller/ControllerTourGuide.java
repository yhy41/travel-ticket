/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Application;
import model.Customer;
import model.PaketWisata;
import model.TourGuide;
import tableModel.tbModCustomer;
import tableModel.tbModPenugasanTG;
import view.GUITourGuide;
import view.viewListCs;
/**
 *
 * @author Lenovo
 */
public class ControllerTourGuide extends MouseAdapter{
    private Application model;
    private GUITourGuide viewTG;
    private tbModPenugasanTG tbModPNGSTG;
    
    private viewListCs viewCs;
    private tbModCustomer tbModLsCs;
    
    public ControllerTourGuide(Application model, GUITourGuide viewTG){
        this.viewTG = viewTG;
        this.model = model;
        
        model.loadAllTourguide();
        
        viewTG.addMouseListener(this);
        //table model
        tbModPNGSTG = new tbModPenugasanTG();
        viewTG.getTbViewPNGS().setModel(tbModPNGSTG);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        
    //Search Edit TG
        if(source.equals(viewTG.getBtnSearchUDP())){
            TourGuide tg = model.getTourGuide(viewTG.getTfIdSearchTGUDP().getText());
            if(tg != null){
                viewTG.getTfNamaUDP().setText(tg.getNama());
                if(tg.getJenisKelamin() == "Laki-Laki"){
                    viewTG.getBgJKUDP().setSelected(viewTG.getRbLakiUDP().getModel(), true);
                }else{
                    viewTG.getBgJKUDP().setSelected(viewTG.getRbPerempuanUDP().getModel(), true);
                }
                viewTG.getTfUmurUDP().setText(String.valueOf(tg.getUmur()));
                viewTG.getTaAlamatUDP().setText(tg.getAlamat());
                viewTG.getTfKontakUDP().setText(tg.getKontak());
                JOptionPane.showMessageDialog(viewTG, "Id Valid");            
            }else{
                JOptionPane.showMessageDialog(viewTG, "Id Tidak Valid");
            }
        }
    //Ubah Data Pribadi TG 
        else if(source.equals(viewTG.getBtnEditUDP())){
            TourGuide tg = model.getTourGuide(viewTG.getTfIdSearchTGUDP().getText());
            String jk = null;
            if(viewTG.getRbLakiUDP().isSelected()){
                jk = "Laki-Laki";
            }else{
                jk = "Perempuan";
            }
            model.editTourGuide(tg, viewTG.getTfNamaUDP().getText(), jk, Integer.parseInt(viewTG.getTfUmurUDP().getText()), viewTG.getTaAlamatUDP().getText(), viewTG.getTfKontakUDP().getText());
            JOptionPane.showMessageDialog(viewTG, "Data Telah Diedit");
            resetViewUDP();
        }
    //Search Id Penugasan Tour Guide
        else if(source.equals(viewTG.getBtnSearchIdTGPNGS())){
            TourGuide tg = model.getTourGuide(viewTG.getTfIdSearchTGPNGS().getText());
            if(tg != null){
                List<String[]> listPngs = model.searchPenugasan(tg);
                tbModPNGSTG.updateTable(listPngs);
                JOptionPane.showMessageDialog(viewTG, "Id Valid");
            }else{
                JOptionPane.showMessageDialog(viewTG, "Id Tidak Valid");
            }
        }
    //col customer clicked
        else if(source.equals(viewTG.getTbViewPNGS())){
            if(viewTG.getTbViewPNGS().getSelectedColumn() == 3){
                System.out.println("killllkkk");
                viewCs = new viewListCs();
                tbModLsCs = new tbModCustomer();
                
                //mencari pw
                String nama = viewTG.getTbViewPNGS().getValueAt(viewTG.getTbViewPNGS().getSelectedRow(), 0).toString();
                System.out.println("string nama : "+nama);
                PaketWisata pw = model.getPaketWisatabyName(nama);
                System.out.println("nama paket wisata : "+pw.getNama());
                //mencari customer
                tbModLsCs.updateTable(model.searchCustomerDiPenugasan(model.getTourGuide(viewTG.getTfIdSearchTGPNGS().getText()),pw));
                
                viewCs.getTbViewCs().setModel(tbModLsCs);
                viewCs.setVisible(true);
            }
        }
    }
    
    public void resetViewUDP(){
        viewTG.getTfIdSearchTGUDP().setText("");
        viewTG.getTfNamaUDP().setText("");
        viewTG.getBgJKUDP().clearSelection();
        viewTG.getTfUmurUDP().setText("");
        viewTG.getTaAlamatUDP().setText("");
        viewTG.getTfKontakUDP().setText("");
    }
}
