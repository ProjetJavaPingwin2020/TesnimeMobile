/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

//warini el service
import Entity.Articles_especes;
import Entity.Commentaire;
import com.codename1.components.ImageViewer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class RefugieService {
         public ConnectionRequest req;
         public static RefugieService  instance=null;
         public boolean resultOK;
         public ArrayList<Articles_especes> tasks;
         public Articles_especes ti=new Articles_especes();
              public ArrayList<Commentaire> tasks1;

    public RefugieService() {
        req = new ConnectionRequest();    
    }
           
     public static RefugieService getInstance() {
        if (instance == null) {
            instance = new RefugieService();
        }
        return instance;
    }  
     
     public ArrayList<Articles_especes> parseRefugie(String jsonText)throws Exception{
        try {
            
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.print("size :"+list.size());
            for(Map<String,Object> obj : list)
            {
               Articles_especes t = new Articles_especes();
                float id = Float.parseFloat(obj.get("id").toString());
            float  numlike = Float .parseFloat(obj.get("numlike").toString());
                t.setId((int)id);
                t.setTitre(obj.get("titre").toString());
                t.setContenu(obj.get("contenu").toString());  
                t.setImage(obj.get("image").toString());
                   t.setNumlike((int)numlike);
              
              /*  Map<String, Object> dateF = (Map<String, Object>) obj.get("datePub");
                float ZonedDateTime= Float.parseFloat(dateF.get("timestamp").toString());
                
                Date date = new Date((long) ZonedDateTime);
                t.setDatepub(date);*/
              
               
                tasks.add(t);
            }
            
        } catch (IOException ex) {
            
        }
        return tasks;
    }
     
     
     
      public Articles_especes parseRefugie1(String jsonText){
       
              Articles_especes t = new Articles_especes();
              try {
                        ArrayList<Articles_especes>tasks= new ArrayList<>();
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
                        List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");

     //stana nchouf codi behy bb
     for(Map<String,Object> obj : list){
                float id = Float.parseFloat(obj.get("id").toString());
                
                t.setId((int)id);
                t.setTitre(obj.get("titre").toString());
                t.setContenu(obj.get("contenu").toString());
                
                //t.setOrigine(obj.get("ligne").toString());
             Dialog dlg = new Dialog("Articles_especes!");
dlg.setLayout(new BoxLayout(600));
                
                tasks.add(t);}
            
        } catch (IOException ex) {
            
        }
        return t;
    }
    
      
       
        public Articles_especes getRefugie(int id){
        String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/mobileApi/"+id;
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
        
        //aatinigui mte3 ajout
         public boolean addRefugie(Articles_especes t) {
        String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/ajouterArticle?Titre="+t.getTitre()+"&Contenu="+t.getContenu()+"&image="+t.getImage()+"&numlike="+t.getNumlike(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() { //lina twid image
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
         
         
         
     public ArrayList<Articles_especes> suppRefugie(int id){
        String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/supp_refMob/"+id+"";
       System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                
                try {
                    tasks = parseRefugie(new String(req.getResponseData()));
                } catch (Exception ex) {
                
                }
                    //System.out.println("chnia mochkol "+tasks);
                    req.removeResponseListener(this);
              
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
     
    
  public boolean modifierRefugie(Articles_especes t) {
        String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/modifMob?id="+t.getId()+"&Titre="+t.getTitre()+"&Contenu="+t.getContenu()+
                "&numlike="+t.getNumlike();
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
     
  
  
        public ArrayList<Articles_especes> getAllRefugie(){
        String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/afficherArticle";
       //System.out.println(url);
        req.setUrl(url);
        
        req.setPost(false);
         
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                   // System.out.println(url);
                    tasks = parseRefugie(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
        
        
        
        /*****************/
             public ArrayList<Commentaire> parseCamp(String jsonText)throws Exception{
        try {
            tasks1=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.print("size :"+list.size());
            for(Map<String,Object> obj : list){
                Commentaire t = new Commentaire();
               float id = Float.parseFloat(obj.get("id").toString());
               // float lieu= Float.parseFloat(obj.get("lieu").toString());
             
                t.setId((int)id);
                t.setContenu(obj.get("Contenu").toString());
                System.out.println(t.getContenu());
               // t.setLieu((int)lieu);
                 
                
                
                tasks1.add(t);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return tasks1;}
        public ArrayList<Commentaire> getAllCamp(){
            String url ="http://localhost/integration/test1.1/web/app_dev.php/mobile/tasks/afficherArticle";
       //System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
                try {
                    tasks1 = parseCamp(new String(req.getResponseData()));
                   
                    req.removeResponseListener(this);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks1;
    }
   /*public Container getList2(Resources theme) {
                           
        
        Container container1All = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        ArrayList<Articles_especes> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/test1.1/web/app_dev.php/api/tasks/afficherArticle");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                ArrayList<Articles_especes> listTasks = new ArrayList<>();
                JSONParser jsonp = new JSONParser();
                
                try {
                    //renvoi une map avec clé = root et valeur le reste
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                   // System.out.println("roooooot:" +tasks.get("root"));

                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

                    for (Map<String, Object> obj : list) {
                        Articles_especes task = new Articles_especes();
                         task.setTitre(obj.get("titre").toString());
                         
                           
                        float id = Float.parseFloat(obj.get("user").toString());
                       
                       
               
                      
                        Label titre=new Label("Titre :"+obj.get("titre").toString());
                        Label lauteur=new Label(obj.get("user").toString());
                        Label adresse=new Label("Adresse"+obj.get("adresse").toString());
         EncodedImage encImg  = EncodedImage.createFromImage(UIManager.initFirstTheme("/theme").getImage("load.gif")
                .fill(125, 175),false);
        URLImage imgUrl = URLImage.createToStorage(encImg, task.getPhoto(),"http://localhost/test1.1/web/front/img/"+task.getPhoto()
                ,URLImage.RESIZE_SCALE_TO_FILL);
        
        ImageViewer image = new ImageViewer(imgUrl);
                        System.out.println(task.getPhoto());
        

                       
                        Button editBtn = new Button("Edit");
        editBtn.getUnselectedStyle().setFgColor(5542241);
                        editBtn.addActionListener((e) -> {
                        new RefugieService().modifierRefugie(ti);

        });
                        
                         Button CommentaireButton= new Button ();
                        //System.out.println("------------------");
                        //System.out.println(id);
    
                        Commentaire abonnement=new Commentaire();
                        Service_Commentaire serviceAbonnement= new    Service_Commentaire();
                             
    
                            Container container = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
                            Container container1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                            Container container2 = new Container(new BoxLayout(BoxLayout.X_AXIS));

                            Service_Commentaire.afficherButtonAbonnement(container1, CommentaireButton, abonnement);
                              
container1.add(titre);
container1.add(adresse);
container1.add(CommentaireButton);
container1.add(image);


container2.add(editBtn);
//container2.add(fbbtn);



        container.add(BorderLayout.CENTER, container1);

    container.add(BorderLayout.SOUTH, container2);
    
                        
//                        Container c=new Container();
//                        c.addAll(titre,lauteur,adresse,nbr);
                  container1All.add(container);
                        
                        
                        

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return container1All;
    }*/
}
