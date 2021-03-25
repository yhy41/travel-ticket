package controller;

import java.sql.SQLException;
import java.text.ParseException;
import view.*;
import model.*;

public class ControllerMainMenu {
    private Application model;
    private GUIMainMenu viewMM;
    private GUIAdmin viewAdm;
    private GUITourGuide viewTG;
    private GUICustomer viewCs;
    
    public ControllerMainMenu(Application model, GUIMainMenu viewMM) throws ParseException, SQLException{
        this.model = model;
        this.viewMM = viewMM;
        viewTG = new GUITourGuide(this,model);
        viewCs = new GUICustomer(this,model);
        viewAdm = new GUIAdmin(this,model);
    }
    
    public void menuAdmin(){
        viewAdm.setVisible(true);
        viewMM.setVisible(false);
    }
    
    public void menuTourGuide(){
        viewTG.setVisible(true);
        viewMM.setVisible(false);
    }
    
    public void menuCustomer(){
        viewCs.setVisible(true);
        viewMM.setVisible(false);
    }
    
    public void toMainMenu(){
        viewMM.setVisible(true);
        viewAdm.setVisible(false);
        viewTG.setVisible(false);
        viewCs.setVisible(false);
    }
}
