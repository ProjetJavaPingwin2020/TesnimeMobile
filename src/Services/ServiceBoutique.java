/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import Entity.Categorie;
import Entity.Commande;
import Entity.FosUser;
import Entity.FosUser1;
import Entity.Produit;

import Entity.Ratingp;
import Entity.login;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceBoutique {

   
    
    
   
    ArrayList<login> login = new ArrayList<>();
    ArrayList<FosUser1> allusers = new ArrayList<>();
    ArrayList<Produit> allproducts = new ArrayList<>();
    ArrayList<Commande> allcommande = new ArrayList<>();
    ArrayList<Ratingp> allrating = new ArrayList<>();
   
   
    
 public login login() {       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/yassine");  
       
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                try {
                    
                    login = parseLogin(new String(con.getResponseData()));
                    System.out.println("****"+login);
                   
                } catch (IOException ex) {
             System.out.println("erreur hné");
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return login.get(0);
    }
 
 
 
  public ArrayList<login> parseLogin(String json) throws IOException {

        ArrayList<login> log = new ArrayList<>();
 
        JSONParser j = new JSONParser();
        Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
        List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
        for (Map<String, Object> obj : list) {
            //Création des tâches et récupération de leurs données
            login e = new login();
            
            float id = Float.parseFloat(obj.get("id").toString());
            
            e.setId((int) id);
             
             
             float id_user = Float.parseFloat(obj.get("idUser").toString());
            
            e.setId_user((int)id_user);
           
             e.setMail(obj.get("mail").toString());
             e.setUsername(obj.get("username").toString());
            
           
             
            
            log.add(e);
            
        }
        
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
     
        return log;

    }
  
  
  public ArrayList<FosUser1> users() {       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/login2");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBoutique ser = new ServiceBoutique();
                try {
                    allusers = ser.parseUsers(new String(con.getResponseData()));
                } catch (IOException ex) {
                   
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return allusers;
    }
  
  public ArrayList<FosUser1> parseUsers(String json) throws IOException {

        ArrayList<FosUser1> log = new ArrayList<>();

        JSONParser j = new JSONParser();
        Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
        List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
        for (Map<String, Object> obj : list) {
            //Création des tâches et récupération de leurs données
            FosUser1 e = new FosUser1();
            
            float id = Float.parseFloat(obj.get("id").toString());
            e.setId((int) id);
            
            e.setEmail(obj.get("email").toString());
            e.setUsername(obj.get("username").toString());
           e.setPassword(obj.get("password").toString());
           
            
            
            log.add(e);
            
        }
        
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
     
        return log;

    }
  
  public boolean connexion(String pass,String username)
  
  {
       final UpdatableBCrypt s2= new UpdatableBCrypt(10);
     ArrayList<FosUser1>  l2=users();
      
           for (FosUser1 u : l2)
           {
 String pwd=convert(u.getPassword());
  
                   if  ( s2.verifyHash(pass,pwd) && username.equals(u.getUsername()))
            {
                
                return true;
            }
                   
               }

   return false;  
}
  
  public String convert(String pass)
  {
       if (pass.startsWith("$2a"))
               {
                   return pass;
               }
  else 
               {
                   String strCopy1 = (pass.substring(3));
                  return "$2a"+strCopy1;
               }
  }
  
  
  public ArrayList<FosUser1> parseFos(String json,String username) throws IOException {

       
FosUser1 e = new FosUser1();
        
        JSONParser j = new JSONParser();
        Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
        List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
        
        
        for (Map<String, Object> obj : list) {
            //Création des tâches et récupération de leurs données
           
            
            if  (obj.get("username").toString().equals(username))
            {
                 float id = Float.parseFloat(obj.get("id").toString());
            e.setId((int) id);
            
            e.setEmail(obj.get("email").toString());
            e.setUsername(obj.get("username").toString());
           e.setPassword(obj.get("password").toString());
                    allusers.add(e);
                
           }

           }
           return allusers;
        }
 
  
  
  public ArrayList<FosUser1> FosUser2(String username) {       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/user/login3/"+username);  
      
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBoutique ser = new ServiceBoutique();
                try {
                    allusers = ser.parseFos(new String(con.getResponseData()),username);
                } catch (IOException ex) {
                   
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return allusers;
    }
  
  public ArrayList<Produit> parseProduits(String json) throws IOException {

        ArrayList<Produit> log = new ArrayList<>();

        JSONParser jsonp = new JSONParser();
              
               
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(json.toCharArray()));
        List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
        for (Map<String, Object> obj : list) {
               //-----------------------------------CATEGORY----------------------------------------------------
                Map<String, Object> listRecupCategory = null;
 Produit e = new Produit();
              Categorie c = new Categorie();
                if (obj.get("categorie") != null) {

                    listRecupCategory = (Map<String, Object>) obj.get("categorie");

                    e.setNomcat((listRecupCategory.get("nom").toString()));
                   
                }
            //Création des tâches et récupération de leurs données
           
            
            float id = Float.parseFloat(obj.get("id").toString());
            e.setId((int) id);
            
//          float categorie= Float.parseFloat(obj.get("categorie").toString());
          //e.setCategorie((int)categorie);
          e.setNom(obj.get("nom").toString());
          e.setDescription(obj.get("description").toString());
          
          float prix= Float.parseFloat(obj.get("prix").toString());
          e.setPrix((double)prix);
          float rating = Float.parseFloat(obj.get("rating").toString());
          e.setRating(rating);
          float quantite= Float.parseFloat(obj.get("quantite").toString());
          e.setQuantite((int)quantite);
          e.setCategorie((int)quantite);
          
          e.setNomimage(obj.get("nomImage").toString());
          
          
            
            
            log.add(e);
            
        }
  return log;
}
  
 public ArrayList<Produit> consulterproduit() {       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/products");  
        System.out.println(con.getUrl());
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBoutique ser = new ServiceBoutique();
                try {
                    allproducts = ser.parseProduits(new String(con.getResponseData()));
                } catch (IOException ex) {
                   
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return allproducts;
    }
 
 public void AjouterCommande(int id_prod,int quantite)
 {
 
                    ConnectionRequest con = new ConnectionRequest();
                   
                    con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/commandemobile?"
                            + "id_prod="+id_prod
                            + "&quantite="+quantite
                       );
                    System.out.println(con.getUrl());
System.out.println(con.getUrl());
                    

                  
 

}
 public ArrayList<Commande> consultercommande(){ //selon id userrr connecté
              login l =new login();
              l=login();
             ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/paniermobile/"+l.getId_user();
        con.setUrl(url);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                try {
                    allcommande = parseCommande(new String(con.getResponseData()));
                } catch (IOException ex) {
                  
                }
      
                con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return allcommande;
    }     
       //////////////supprimer commande///////

 public void supprimerproduit(int id) {
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/annulercommande/" + id;
        System.err.println(url);
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con); //appel asynchrone

    }
       public ArrayList<Commande> parseCommande(String json) throws IOException {

        ArrayList<Commande> log = new ArrayList<>();

        JSONParser j = new JSONParser();
        Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
        List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
        for (Map<String, Object> obj : list) {
               //-----------------------------------CATEGORY----------------------------------------------------
                Map<String, Object> listRecupCategory = null;
 Produit e1 = new Produit();
 Commande e= new Commande();
 
              Categorie c = new Categorie();
                if (obj.get("produit") != null) {

                    listRecupCategory = (Map<String, Object>) obj.get("produit");

                    e.setNom_prod((listRecupCategory.get("nom").toString()));
                   
                }
                 Map<String, Object> users = null;
 
 FosUser1 e3= new FosUser1();
 
              
                if (obj.get("user") != null) {

                    users = (Map<String, Object>) obj.get("user");

                    e.setNom_client((users.get("username").toString()));
                   
                }
            //Création des tâches et récupération de leurs données
           
            
            float id = Float.parseFloat(obj.get("id").toString());
            e.setId((int) id);
            
//          float categorie= Float.parseFloat(obj.get("categorie").toString());
          //e.setCategorie((int)categorie);
          e.setEtat(obj.get("etat").toString());
          e.setPay(obj.get("pay").toString());
          
          float prix= Float.parseFloat(obj.get("prixtotal").toString());
          e.setPrixtotal((double)prix);
          
          float quantite= Float.parseFloat(obj.get("quantite").toString());
          e.setQuantite((int)quantite);
          
          
         
          
          
            
            
            log.add(e);
            System.out.println(log);
            
        }
  return log;
}
        public void mail(int id){
           
               
            ConnectionRequest con = new ConnectionRequest();
                   
                    con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/mailmobile/"+id
                     
                            );


                 System.out.println(con.getUrl());

                    con.setFailSilently(true);
                    NetworkManager.getInstance().addToQueueAndWait(con);

                        }
         
 public ArrayList<Commande> consultercommande2(){ //selon id userrr connecté
              login l =new login();
              l=login();
             ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/commandeadmin";
        con.setUrl(url);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                try {
                    allcommande = parseCommande(new String(con.getResponseData()));
                } catch (IOException ex) {
                  
                }
      
                con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return allcommande;
    }    
 
  public ArrayList<Ratingp> consulterRating(){ //selon id userrr connecté
              login l =new login();
              l=login();
             ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/ratingadmin";
        con.setUrl(url);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                try {
                    allrating = parseRating(new String(con.getResponseData()));
                } catch (IOException ex) {
                  
                }
      
                con.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return allrating;
    }     
 
  public ArrayList<Ratingp> parseRating(String json) throws IOException {

       
Ratingp e = new Ratingp();
Produit p = new Produit();
FosUser1 u = new FosUser1();


        
        JSONParser j = new JSONParser();
        Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
        List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
        
        
        for (Map<String, Object> obj : list) {
            //Création des tâches et récupération de leurs données
           if (obj.get("produit") != null) {
            Map<String, Object> prod = null;
                    prod = (Map<String, Object>) obj.get("produit");

                    e.setProduct((prod.get("nom").toString()));
                   
                }
            
            //Création des tâches et récupération de leurs données
           if (obj.get("user") != null) {
            Map<String, Object> prod = null;
                    prod = (Map<String, Object>) obj.get("user");

                    e.setNom_user((prod.get("username").toString()));
                   
                }
            
            
                 float id = Float.parseFloat(obj.get("id").toString());
            e.setId((int) id);
            
            e.setDegre(obj.get("degre").toString());
            e.setCommentaire(obj.get("commentaire").toString());
                    allrating.add(e);
                
           
           }
           return allrating;
        }
 
}
