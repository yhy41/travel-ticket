/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodFungsional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class dt {
    
    public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    public static String ubahPosisi2(String date){
        String newDate = "";
        newDate += date.substring(6,10)+"-";
        newDate += date.substring(3,5)+"-";
        newDate += date.substring(0,2);
        return newDate;
    }
    public static String ubahPosisi(String date){
        String newDate = "";
        newDate += date.substring(8,10)+"-";
        newDate += date.substring(5,7)+"-";
        newDate += date.substring(0,4);
        return newDate;
    }
    
    public static boolean isValid(Date tglB, Date tglP){
        if(tglB.before(tglP)){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean isBentrok(Date tglB, Date tglP, Date tglBC, Date tglPC){
        if(tglP.before(tglBC)){
            return false;
        }else if(tglB.after(tglPC)){
            return false;
        }else{
            return true;
        }
    }
    
    public static void main(String[] args) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(2019,1,2);
        System.out.println(format.format(date));
        System.out.println(ubahPosisi2("01-02-2019"));
    }
}
