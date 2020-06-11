/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Formateur;
import Entity.Formation;
import Entity.Reservation;
import GuiForm.ShowFormations;
import GuiForm.addFormationForm;
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
public class ServiceFormation {

    public ArrayList<Formation> formations;
    public String result = "";
    public static ServiceFormation instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceFormation() {
        req = new ConnectionRequest();
    }

    public static ServiceFormation getInstance() {
        if (instance == null) {
            instance = new ServiceFormation();
        }
        return instance;
    }

    public ArrayList<Formation> parseformations(String jsonText) {
        try {
            formations = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Formation f = new Formation();
              
                float IdFormation = Float.parseFloat(obj.get("id").toString());
                f.setId((int) IdFormation);
                 Map<String, Object> listRecupFormateur = null;
                  Formateur ff = new Formateur();
                     if (obj.get("formateur") != null) {

                    listRecupFormateur = (Map<String, Object>) obj.get("formateur");

                    f.setNomformateur((listRecupFormateur.get("nom").toString()));
                   
                }
           //     float formateur = Float.parseFloat(obj.get("formateur").toString());
             //   f.setFormateur((int) formateur);
                f.setNom(obj.get("nom").toString());
                f.setType(obj.get("type").toString());

                f.setLieu(obj.get("lieu").toString());
                f.setDescription(obj.get("description").toString());

                float NbrPlace = Float.parseFloat(obj.get("nbrplace").toString());
                f.setNbrplace((int) NbrPlace);

            
              
                f.setNomImage(obj.get("nomImage").toString());

                formations.add(f);
            }

        } catch (IOException ex) {

        }

        return formations;
    }

    //Affichage formation
    public ArrayList<Formation> getAllFormations() {
        String url = Statics.BASE_URL + "ListFormationMobile";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                formations = parseformations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return formations;
    }

    public boolean AjouterReservation(Reservation r) {

        String url = Statics.BASE_URL + "ajouterResMobile?idformation=" + r.getIdformation() + "&avis=" + r.getAvis() + "&idu=" + r.getIdu(); //création de l'URL
        System.out.println(url);
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

    ////Ajout d'une formation
    public void ajoutFormation(Formation ta) {
        ConnectionRequest con = new ConnectionRequest();
        System.out.println(UserService.user.getId());
        String date = addFormationForm.picker.getText();

        String Url = Statics.BASE_URL + "newFormation/"
                + "?nom=" + ta.getNom()
                + "&type=" + ta.getType()
                + "&lieu=" + ta.getLieu()
                + "&description=" + ta.getDescription()
                //   + "&heure=" + ta.getHeure()
                + "&nbrplace=" + ta.getNbrplace()
                + "&formateur=" + ta.getFormateur()
                + "&nomImage=" + ta.getNomImage()
                + "&date=" + date;
        con.setUrl(Url);
        System.out.println(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    ////suppression
    public String DeleteFormation(Formation c) {
        String url = Statics.BASE_URL + "deleteFormationMobile/?id=" + c.getId();
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        System.out.println(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    String data = new String(req.getResponseData());
                    JSONParser j = new JSONParser();
                    Map<String, Object> tasksListJson;
                    tasksListJson = j.parseJSON(new CharArrayReader(data.toCharArray()));
                    result = (String) tasksListJson.get("body");

                } catch (IOException ex) {
                    ex.getMessage();
                }
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
    ////Modification

    public void modifierFormation(Formation ta, Resources res) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/updateFormation/" + ta.getId()
                + "?nom=" + ta.getNom()
                + "&lieu=" + ta.getLieu()
                + "&type=" + ta.getType()
                + "&description=" + ta.getDescription()
                + "&nbrplace=" + ta.getNbrplace();
        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
            Dialog.show("Succés", "Formation modifiée", "ok", null);
            ShowFormations a = new ShowFormations(res);
            a.show();

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

      ////Affichage de ma participation
    public ArrayList<Formation> getListMyReservation() {
        ArrayList<Formation> listTasks = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/integration/web/app_dev.php/mobile/MyRes/" + UserService.user.getId());

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();

                try {
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(tasks);
                    //System.out.println(tasks);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                    for (Map<String, Object> obj : list) {
                        Formation task = new Formation();
                        task.setId((int) Float.parseFloat(obj.get("id").toString()));
                        task.setNom(obj.get("nom").toString());

                        task.setType(obj.get("type").toString());
                        task.setLieu(obj.get("lieu").toString());
                        task.setDescription(obj.get("description").toString());
                        //  task.setHeure(obj.get("heure").toString());
                        task.setNbrplace((int) Float.parseFloat(obj.get("nbrplace").toString()));
                        task.setNomImage(obj.get("nomImage").toString());
                        listTasks.add(task);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listTasks;
    }
    //////rechercher Participant 

    public static ArrayList<Reservation> ChercherRes(int idformation, int idu) {
        ArrayList<Reservation> listParticipant = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/findRes/" + idformation + "/" + UserService.user.getId());
        con.addResponseListener((NetworkEvent evttt) -> {
            JSONParser jsonp = new JSONParser();
            try {
                Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                System.out.println(tasks);
                List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                System.out.println(list);
                for (Map<String, Object> obj : list) {
                    Reservation task = new Reservation();
                    task.setAvis(obj.get("avis").toString());
                    float idr = Float.parseFloat(obj.get("idr").toString());
                    task.setIdr((int) idr);
                    task.setEtat(obj.get("etat").toString());
                    listParticipant.add(task);
                    System.out.println("mella hkeya");
                }
            } catch (IOException ex) {
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        System.out.println(listParticipant);
        return listParticipant;
    }
    ////Donner mon avis

    public void DonnerAvisFormation(Formation ta, String k) {
        ConnectionRequest con = new ConnectionRequest();
        System.out.println(UserService.user.getId());
        int idu = UserService.user.getId();
        int idformation = ta.getId();
        ServiceFormation SF = new ServiceFormation();
        Reservation listp = SF.ChercherRes(idformation, idu).get(0);
        System.out.println(listp);
        int j = 0;

        String Url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/AvisFormation/" + idu + "/" + idformation
                + "?avis=" + k;

        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
            Dialog.show("Succés", "Avis enregistré! Merci d'avoir partager votre avis avec nous ", "ok", null);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

}
