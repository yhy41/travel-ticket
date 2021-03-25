package model;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Lenovo
 */
public class TourGuide extends Person { 
    public TourGuide(String id,String nama, String jenisKelamin, int umur,
                    String kontak,String alamat){
        super(id,nama,jenisKelamin,umur,kontak,alamat);
        
    }
    
    public List<PaketWisata> getLsPaketWisataTGS(List<PaketWisata> listPW){
        List listPWTGS = new ArrayList();
        for(PaketWisata pw: listPW){
            for(int i = 0; i<pw.getListGuide().size(); i++){
                if(this.getNama() == pw.getListGuide().get(i).getNama()){
                    listPWTGS.add(pw);
                    break;
                }
            }
        }
        return listPWTGS;
    }

    public List<List<String>> getLsPWnCsTGS(List<Customer> listCs, List<PaketWisata> listPW){
        List<List<String>> listPWnCsTGS = new ArrayList<>();

        List<PaketWisata> listPWTGS = new ArrayList(); 
        listPWTGS = getLsPaketWisataTGS(listPW);
        
        for(Customer cs: listCs){
            for(Pemesanan psn: cs.getListPesanan())
                for(PaketWisata pw: psn.getListPaket())
                    for(int i = 0; i<listPWTGS.size(); i++){
                        if(listPWTGS.get(i).getId() == pw.getId()){
                            List<String> innerListPWnCsTGS = new ArrayList<>();
                            innerListPWnCsTGS.add(pw.getId());
                            innerListPWnCsTGS.add(cs.getId());
                            listPWnCsTGS.add(innerListPWnCsTGS);
                        }
                    }
        }
        
        //SORTING PADA LIST SEKARANG
//        List<List<String>> newListPWnCsTGS = new ArrayList<>();
//        
//        
//        String idPW = listPWnCsTGS.get(0).get(0);
//        String idCs = listPWnCsTGS.get(0).get(1);
//        listPWnCsTGS.remove(0);
//        
//        List<String> inLs = new ArrayList();
//        inLs.add(idPW);
//        inLs.add(idCs);
//        
//        for(List<String> pw : listPWnCsTGS){
//            if(pw.get(0) == idPW){
//                inLs
//            }
//        }
//        newListPWnCsTGS.add()

        return listPWnCsTGS;
    }
}
