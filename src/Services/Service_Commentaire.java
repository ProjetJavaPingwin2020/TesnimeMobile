/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Entity.Articles_especes;
import Entity.Commentaire;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Service_Commentaire {
         public ConnectionRequest req;
         public static Service_Commentaire  instance=null;
         public boolean resultOK;
         public ArrayList<Commentaire> Commentaires;
         public Commentaire ti=new Commentaire();
              public ArrayList<Articles_especes> Article1;

    public Service_Commentaire  () {
        req = new ConnectionRequest();    
    }
           
     public static Service_Commentaire getInstance() {
        if (instance == null) {
            instance = new Service_Commentaire();
        }
        return instance;
    }  
     
     public ArrayList<Commentaire> parseRefugie(String jsonText)throws Exception{
        try {
            Commentaires=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            //hetha houa affichage? eyy
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.print("size :"+list.size());
            for(Map<String,Object> obj : list)
            {
               Commentaire t = new Commentaire();
                float id = Float.parseFloat(obj.get("id").toString());
                Map<String, Object> date = (Map) obj.get("date");
              long dateTimeStamp = (long) (Double.parseDouble(date.get("datePub").toString())*1000);
             //    Map<String, Object> dateF = (Map<String, Object>) obj.get("datePub");
             Date d2 = new Date(dateTimeStamp);
    
             
              //  float dateTimeStamp = Float.parseFloat(dateF.get("timestamp").toString())*1000);
               // Date date = new Date((long)dateTimeStamp);
               
                t.setDatePub(d2);
                t.setId((int)id);
                
                t.setContenu(obj.get("contenu").toString());
               t.setArticle((int)Float.parseFloat(obj.get("article").toString()));
               Commentaires.add(t);
            }
            
        } catch (IOException ex) {
            
        }
        return Commentaires;
    }
       public ArrayList<Commentaire> parseRefugie2(String jsonText)throws Exception{
        try {
            Commentaires=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            //hetha houa affichage? eyy
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.print("size :"+list.size());
            for(Map<String,Object> obj : list)
            {
               Commentaire t = new Commentaire();
                float id = Float.parseFloat(obj.get("id").toString());
                 Map<String, Object> dateF = (Map<String, Object>) obj.get("datePub");
                 Map<String, Object> article = (Map<String, Object>) obj.get("article");

                float dateTimeStamp = Float.parseFloat(dateF.get("timestamp").toString());
                Date date = new Date((long)dateTimeStamp);
                t.setDatePub(date);
                t.setId((int)id);
                
                t.setContenu(obj.get("contenu").toString());
               t.setArticle((int)Float.parseFloat(article.get("id").toString()));
               t.setContenuArticle(article.get("contenu").toString());
              //taffichi contenu mte3 article m3ahh el les com ? ey w
               Commentaires.add(t);
            }
            
        } catch (IOException ex) {
            
        }
        return Commentaires;
    }
     
     
      public Commentaire parseRefugie1(String jsonText){
       
             Commentaire t = new Commentaire();
              try {
                        ArrayList<Commentaire>tasks= new ArrayList<>();
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
     
                float id = Float.parseFloat(tasksListJson.get("id").toString());
                
                t.setId((int)id);
       
                t.setContenu(tasksListJson.get("contenu").toString());
    
               t.setArticle((int) tasksListJson.get("article"));
                //t.setOrigine(obj.get("ligne").toString());
             
                
                tasks.add(t);
            
        } catch (IOException ex) {
            
        }
        return t;
    }
    
      //win m3aytetelha el fci ena m3aytla getAllCommentaire
       
        public Commentaire getCommentaire(int id){
        String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/commentget/"+id;
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                ti = parseRefugie1(new String(req.getResponseData()));
                 //System.out.println("chnia mochkol "+tasks);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ti;
    }
        
        
         public boolean addCommentaire(Commentaire t) {
        String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/ajouter_Commentaire?article="
                +t.getArticle()+"&contenu="+t.getContenu(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
         
         
         
     public ArrayList<Commentaire> suppCommentaire(int id){
        String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/supp_Commentaire/"+id+"";
       System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                
                try {
                  Commentaires = parseRefugie(new String(req.getResponseData()));
                } catch (Exception ex) {
                
                }
                    //System.out.println("chnia mochkol "+tasks);
                    req.removeResponseListener(this);
              
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Commentaires;
    }
   
  public boolean modifierCommentaire(Commentaire t) {
        String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/mobileApi/modifCommentaire?id="+t.getArticle()+"&contenu="+t.getContenu();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
     
  
  
        public ArrayList<Commentaire> getAllCommentaire(){
        String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/afficher_Commentaire";
       //System.out.println(url);
        req.setUrl(url);
        
        req.setPost(false);
         
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                   // System.out.println(url);
                 Commentaires = parseRefugie2(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Commentaires;
    }
        
        //article  wala commentaire ? Commentaire
        
        /*****************/
             public ArrayList<Articles_especes> parseArticle(String jsonText)throws Exception{
        try {
            Article1=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.print("size :"+list.size());
            for(Map<String,Object> obj : list){
                Articles_especes A= new Articles_especes();
              Commentaire t=new Commentaire();
               float id = Float.parseFloat(obj.get("id").toString());
               // float lieu= Float.parseFloat(obj.get("lieu").toString());
                A.setId((int)id);
                
               t.setArticle(A.getId());
              A.setContenu(obj.get("contenu").toString());
                System.out.println("hetha houa id "+A.getId());
               // t.setLieu((int)lieu);
                 
               A.setTitre(obj.get("titre").toString());
                
              Article1.add(A);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return  Article1;}
        public ArrayList<Articles_especes> getAllArticle(){
            String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/afficherArticless";
       //System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
                try {
                    Article1 = parseArticle(new String(req.getResponseData()));
                   
                    req.removeResponseListener(this);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  Article1;
    }


     
}
