/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import Entity.Formateur;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author asus
 */
public class ServiceFormateur {
    
      public ArrayList<Formateur> formateurs;
    public  String  result="";
    public static ServiceFormateur instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceFormateur() {
         req = new ConnectionRequest();
    }

    public static ServiceFormateur getInstance() {
        if (instance == null) {
            instance = new ServiceFormateur();
        }
        return instance;
    }
    
    public ArrayList<Formateur> parseformations(String jsonText){
        try {
            formateurs=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Formateur f = new Formateur();
                float IdFormation = Float.parseFloat(obj.get("id").toString());
                  f.setId((int)IdFormation);
                  f.setNom(obj.get("nom").toString());
                  f.setPrenom(obj.get("prenom").toString());
                  
               
                  
                 
                  f.setNomImage(obj.get("nomImage").toString());
               
                  
                 
                 
                formateurs.add(f);
            }
            
            
        } catch (IOException ex) {
            
        }
       
        return formateurs;
    }
    
    //Affichage formation
    public ArrayList<Formateur> getAllFormateurs(){
        String url = Statics.BASE_URL+"ListFormateurMobile";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                formateurs = parseformations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);     
                return formateurs;
    }
   
 
   
 
   







    
}
