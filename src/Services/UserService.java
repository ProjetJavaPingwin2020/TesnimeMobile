/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.FosUser;
import Entity.login;
import GuiForm.ListFormation;
import GuiForm.MenuForm;


import GuiForm.MenuFormAdmin;


import GuiForm.SignInForm;
import GuiForm.SignUpForm;


import GuiForm.WelcomeForm;

import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asus
 */
public class UserService {

    public static FosUser user = new FosUser();
    // public static User onlineId = new User();

    int temp;
 public void RegisterUser(Resources res) {
        String rol = "0";
        String nomlog = SignUpForm.nom.getText();
        String numtel = SignUpForm.telephone.getText();
        String adresse = SignUpForm.adresse.getText();
        String email = SignUpForm.email.getText();
        String date = SignUpForm.picker.getText();
        String surnom = SignUpForm.surnom.getText();
        String motdepasse = SignUpForm.motdepasse.getText();

        String role = "";
        /*affichage données test
        System.out.println(userlog);
        System.out.println(pass);
        System.out.println(conpass);
        System.out.println(numtel);
        System.out.println(adresse);
        System.out.println(role);
        System.out.println(email);
        System.out.println(role);
        System.out.println(role);*/

        if (numtel.equals("")) {

            InteractionDialog dlg = new InteractionDialog("Notification");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new SpanLabel("Un champ est vide! Veuillez le remplir."));
            Button close = new Button("Close");
            close.addActionListener((ee) -> dlg.dispose());
            dlg.addComponent(BorderLayout.SOUTH, close);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.show(50, 100, 30, 30);
            return;
        }
        int carteI = 0;
        try {
            carteI = Integer.parseInt(numtel);
        } catch (Exception ex) {

            InteractionDialog dlg = new InteractionDialog("Notification");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new SpanLabel("S'il vous plait, inserez des chiffres dans le champ de Numeros de Tel."));
            Button close = new Button("Close");
            close.addActionListener((ee) -> dlg.dispose());
            dlg.addComponent(BorderLayout.SOUTH, close);
            Dimension pre = dlg.getContentPane().getPreferredSize();
            dlg.show(50, 100, 30, 30);
            return;
        }

//        if (!pass.equals(conpass)) {
//            Dialog.show("error", "please confirm your password ", "cancel", "ok");
//            return;
//        }
//
//
//
        if (nomlog.equals(".") || nomlog.equals("&") || nomlog.equals("é") || nomlog.equals("'")) {
            Dialog.show("Champs Incorrecte", "Corriger le Nom svp  ", "Ok", null);
            return;
        }

        if (nomlog.length() == 0 || motdepasse.length() == 0 || email.length() == 0 || surnom.length() == 0
                || adresse.length() == 0) {
            Dialog.show("Champs Incorrecte", "Tous les champs sont obligatoires", "Ok", null);
            return;
        }

        if (numtel.length() < 8 || numtel.length() > 8 || numtel.equals("^[0-9]{8}$")) {
            Dialog.show("Champs Incorrecte", "Numero de telephone de 8 chiffres  ", "Ok", null);
            return;
        }

        //  else {
        user.setNom(nomlog);
        user.setMotdepasse(motdepasse);

        user.setTelephone(Integer.parseInt(numtel));
        user.setAdresse(adresse);
        user.setEmail(email);
        user.setSurnom(surnom);
        user.setDatedenaissance(date);
        user.setMotdepasse(motdepasse);
        user.setGrade(rol);

        //    }
        ConnectionRequest connectionRequest;
        connectionRequest = new ConnectionRequest() {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                System.out.println(input);

            }

            @Override
            protected void postResponse() {
                if (user.getGrade().equals("0")) {
                     // new ListFormation(res).show();
                    new MenuForm(res).show();
                } else if (user.getGrade().equals("1")) {
              //      new MenuFormAdmin(res).show();

                     new MenuFormAdmin(res).show();
                }
            }
        };

       
        connectionRequest.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/newuser?nom=" + nomlog + "&adresse=" + adresse + "&email=" + email + "&telephone=" + numtel + "&username=" + surnom + "&password=" + "$2y$13$Rj/knfFwy.zMM/UIHHM6rujvqSiLDxEYHPvum.B4PqcDGbQ8Dh/HC" + "&grade=" + rol+"&dateN="+date);
        NetworkManager.getInstance().addToQueue(connectionRequest);

    }


    public void login(Resources res) {
        // TextField userlogin = (TextField) SignInForm.builder.findByName("Username", SignInForm.ctn);
        //TextField passlogin = (TextField) SignInForm.builder.findByName("Password", SignInForm.ctn);
        String userlog = SignInForm.username.getText();
        String passlog = SignInForm.password.getText();

        ConnectionRequest connectionRequest;
        connectionRequest = new ConnectionRequest() {
            @Override
            protected void readResponse(InputStream input) throws IOException {
login e= new login();

                JSONParser json = new JSONParser();
                try {
                    Reader reader = new InputStreamReader(input, "UTF-8");
                    Map<String, Object> data = json.parseJSON(reader);
                    if (data.isEmpty()) {
                        Dialog.show("error", "Username not found ! please retry ", "Cancel", "ok");
                    } else {

                        user.setId((int) Float.parseFloat(data.get("id").toString()));
                        user.setNom(((String) data.get("nom")));
                        user.setMotdepasse(((String) data.get("password")));
//                        user.setTelephone((int) Integer.parseInt(data.get("numtel").toString()));
                        user.setAdresse(((String) data.get("adresse")));
                        user.setEmail(((String) data.get("email")));
                        user.setSurnom(((String) data.get("username")));
                        user.setGrade(((String) data.get("grade")));
//                        user.setRole(((String) data.get("role")));
                        /*Map<String, Object> data2 = (Map<String, Object>) (data.get("datenaissence"));
                        temp = (int) Float.parseFloat(data2.get("timestamp").toString());
                        Date myDate = new Date(temp * 1000L);
                        //user.setDateNaissence(myDate);*/
                        List<String> content = new ArrayList<>();
//                        content.addAll((Collection<? extends String>) (data.get("role")));
//                        user.setRole(content.get(1));
System.out.println(user.getSurnom());
                 UpdateLogin(user.getSurnom())      ;
                    }
                } catch (IOException err) {
                    Log.e(err);
                }
            }

            @Override
            protected void postResponse() {
                System.out.println(user);
                System.out.println(user.getGrade());
                if (passlog.equals("")) {
                    Dialog.show("error", "Please put your password ! ", "cancel", "ok");
                    return;
                }
//                if (!(user.getMotdepasse().equals((passlog)))) {
//                    System.out.println(user.getMotdepasse());
//                    System.out.println(passlog);
//                    Dialog.show("error", "Wrong password please retry! ", "cancel", "ok");
//                    return;
//                }

                    if (user.getGrade().equals("0")) {

                    //new ProfileForm(res).show();
                    new MenuForm(res).show();
                    //    new ListFormation(res).show();
                } else if (user.getGrade().equals("1")) {
              //      new MenuFormAdmin(res).show();

                     new MenuFormAdmin(res).show();
                } else {
                    Dialog.show("error", "smthin wrong ", "cancel", "ok");

                }

            }
        };
        //       System.out.println(passlog);
        System.out.println(userlog);

          connectionRequest.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/finduser/" + userlog);
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }
    
    public void UpdateLogin(String username)
 {
 
                    ConnectionRequest con = new ConnectionRequest();
                   
                    con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/mobile/updatelogin/"
                           
                            +username
                       );
                    System.out.println(con.getUrl());
System.out.println(con.getUrl());
NetworkManager.getInstance().addToQueue(con);
                    
}
}
