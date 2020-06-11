/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Demande;
import Entity.Evenement;
import GuiForm.AddEvenementForm;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author milim
 */
public class ServiceEvenement {

    String a = "";

    public ArrayList<Evenement> getList2() {
        ArrayList<Evenement> listAssos = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/listE");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp = new JSONParser();
                try {
                    Map<String, Object> Evnt = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                  //  System.out.println("msg /////////////////////");
                    //  System.out.println("msg" + Evnt);
                    //  System.out.println("msg /////////////////////");
                    List<Map<String, Object>> list = (List<Map<String, Object>>) Evnt.get("root");
                    for (Map<String, Object> obj : list) {
                        Evenement a = new Evenement();
                     //   System.out.println("//////////////////");
                        //    System.out.println(obj.get("idEvenement").toString());
                        //    System.out.println("//////////////////");
                        float id = Float.parseFloat(obj.get("idEvent").toString());
                        a.setIdEvenement((int) id);
                        System.out.println(a.getIdEvenement());
                        a.setNomEvenement(obj.get("nomEvent").toString());
                        System.out.println(a.getNomEvenement());
                        a.setDescription(obj.get("description").toString());
                        a.setAdresse(obj.get("adresse").toString());
                        a.setDateEvenement(obj.get("date").toString());
                        a.setNomImage(obj.get("nomImage").toString());
                        //int i= Integer.parseInt(obj.get("idUser").toString());
                        //a.setId_user(i);
                        System.out.println(a);

                        listAssos.add(a);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listAssos;
    }

    public String getButtonName(int ide) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/btName?user=" + UserService.user.getId() + "&idevnt=" + ide);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp = new JSONParser();
                try {
                    Map<String, Object> Asso = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    // System.out.println("msg" + Asso);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) Asso.get("root");
                    for (Map<String, Object> obj : list) {
                        a = obj.get("btname").toString();

                    }
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return a;
    }

    public void ajoutVote(int ida, float stars, String desc) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/rating?user=" + UserService.user.getId() + "&idevnt=" + ida + "&star=" + stars + "&desc=" + desc;
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

    public void ajoutAdheration(Demande d) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/newD?user=" + d.getIdu() + "&idevnt=" + d.getIde();

        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

    public ArrayList<Evenement> getListMyEvenements() {
        ArrayList<Evenement> listAssos = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/listMyE?chef=" + UserService.user.getId());
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp = new JSONParser();

                try {
                    Map<String, Object> Asso = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println("msg" + Asso);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) Asso.get("root");
                    for (Map<String, Object> obj : list) {
                        Evenement a = new Evenement();
                        float id = Float.parseFloat(obj.get("idEvent").toString());
                        a.setIdEvenement((int) id);
                        System.out.println(a.getIdEvenement());
                        a.setNomEvenement(obj.get("nomEvent").toString());
                        System.out.println(a.getNomEvenement());
                        a.setDescription(obj.get("description").toString());
                        a.setAdresse(obj.get("adresse").toString());
                        a.setDateEvenement(obj.get("date").toString());
                        a.setNomImage(obj.get("nomImage").toString());
                        //int i= Integer.parseInt(obj.get("idUser").toString());
                        //a.setId_user(i);
                        System.out.println(a);

                        listAssos.add(a);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listAssos;
    }
        public void updateEvnt(Evenement a) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/updateE?nom=" + a.getNomEvenement() + "&adresse=" + a.getAdresse() + "&desc=" + a.getDescription()+ "&ide=" + a.getIdEvenement() ;// création de l'URL
        //String Url = "http://localhost:3309/symfony-api/web/app_dev.php/api/tasks/new?name=task1&status=0";
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
            public void deleteEvnt(Evenement a) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/deleteE?ide=" + a.getIdEvenement();
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
        public void ajoutEvnt(Evenement a) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String date = AddEvenementForm.picker.getText();
        String Url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/newE?nom=" + a.getNomEvenement() + "&adresse=" + a.getAdresse() + "&desc=" + a.getDescription()+ "&user=" + a.getChefId() + "&image=" + a.getNomImage() +"&date="+ date;// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console
        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

}
