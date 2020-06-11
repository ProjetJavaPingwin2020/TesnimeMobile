/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import Entity.Formation;
import Entity.FosUser;
import Entity.Reservation;
import Entity.Session;
import static GuiForm.ShowFormations.form;
import static GuiForm.ShowFormations.ttfFont;
import Services.ServiceFormation;
import Services.UserService;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;


/**
 *
 * @author asus
 */
public class ListFormation extends BaseForm {

    static Form currentForm;
    public static Form f, form;

    private EncodedImage placeHolder;
   FosUser User = Session.getCurrentSession();
ServiceFormation sf = new ServiceFormation();
    public ListFormation(Resources res) {

         super("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Liste des formations");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
       // tb.addSearchCommand(e -> {
     //   });

        Image imge = res.getImage("profile-background.jpg");
        if (imge.getHeight() > Display.getInstance().getDisplayHeight() / 5) {
            imge = imge.scaledHeight(Display.getInstance().getDisplayHeight() / 5);
        }
        ScaleImageLabel sl = new ScaleImageLabel(imge);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

       add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(3,FlowLayout.encloseCenter(new Label(res.getImage(""), "PictureWhiteBackgrond"))     )
                )
        ));
        currentForm = this;
       // currentForm.setTitle("Formation");
       // currentForm.setLayout(BoxLayout.y());

        for (Formation f : ServiceFormation.getInstance().getAllFormations()) {
            Container InfoContainer = new Container(BoxLayout.y());
              System.out.println("nom" +f.getNom()); 
            Label nom = new Label("Nom  : " + f.getNom());
            nom.getAllStyles().setFgColor(0x000000);
            
            Label typee = new Label("Type  : " + f.getType());
               typee.getAllStyles().setFgColor(0x000000);
            Label lieu = new Label("Lieu  : " + f.getLieu());
               lieu.getAllStyles().setFgColor(0x000000);
        
            Label Nombreplace = new Label(String.valueOf("Nombre de places : " +f.getNbrplace()));
               Nombreplace.getAllStyles().setFgColor(0x000000);
                Label detailsFormation = new Label("Voir Details ");
                detailsFormation.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
          

            InfoContainer.add(nom);
            InfoContainer.add(typee);
            InfoContainer.add(Nombreplace);
            InfoContainer.add(detailsFormation);
        //    InfoContainer.add(description);
            
            Container Container = new Container(BoxLayout.x());

           placeHolder = EncodedImage.createFromImage(res.getImage("bla.jpg"), true);
           
            String url = "http://localhost/integration/test1.1/web/imageFormations/" + f.getNomImage();
            System.out.print(url);    
            ConnectionRequest connection = new ConnectionRequest();
            connection.setUrl(url);
           URLImage imgurl = URLImage.createToStorage(placeHolder, url, url);

            ImageViewer img = new ImageViewer(imgurl.scaled(imgurl.getWidth() * 1, imgurl.getHeight() * 1));
            Container.add(img);
            Container.add(InfoContainer);
            currentForm.add(Container);

            detailsFormation.addPointerPressedListener((l) -> {
           // img.addPointerReleasedListener(ev -> {
                FormationDetail(f, res).show();
                  System.out.println(f); 
            });
        }

           Style s = UIManager.getInstance().getComponentStyle("Title");

        TextField searchField = new TextField("", " Search"); // <1>
        searchField.getHintLabel().setUIID("Title");
        searchField.setUIID("Title");
        searchField.getAllStyles().setAlignment(Component.LEFT);
        currentForm.getToolbar().setTitleComponent(searchField);
        FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, s);
        searchField.addDataChangeListener((i1, i2) -> { // <2>
            String t = searchField.getText();
            if (t.length() < 1) {
                for (Component cmp : currentForm.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
            } else {
                t = t.toLowerCase();
                for (Component cmp : currentForm.getContentPane()) {
                    String val = null;
                    if (cmp instanceof Label) {
                        val = ((Label) cmp).getText();
                    } else {
                        if (cmp instanceof TextArea) {
                            val = ((TextArea) cmp).getText();
                        } else {

                            val = (String) cmp.getPropertyValue("text");
                        }
                    }
                    boolean show = val != null && val.toLowerCase().indexOf(t) > -1;
                    cmp.setHidden(!show); // <3>
                    cmp.setVisible(show);
                }
            }
            currentForm.getContentPane().animateLayout(250);
        });
        for (Formation f : ServiceFormation.getInstance().getAllFormations()) {
      {
            Button btn = new Button(f.getNom());
            currentForm.add(btn);
            btn.addPointerReleasedListener(ev -> {
                FormationDetail(f, res).show();
            });
        }
        currentForm.getToolbar().addCommandToRightBar("", searchIcon, (e) -> {
            searchField.startEditingAsync(); // <4>
        });
      

    }
    }

    

    public Form FormationDetail(Formation f, Resources res) {

          ArrayList<Formation> lis = sf.getAllFormations();
       
     //   for (Formation li : lis) {
        
        Form FormationDetail = new Form(BoxLayout.y());

       placeHolder = EncodedImage.createFromImage(res.getImage("bla.jpg"), true);
      String url = "http://localhost/integration/test1.1/web/imageFormations/" + f.getNomImage();
        ConnectionRequest connection = new ConnectionRequest();
       connection.setUrl(url);
       URLImage imgurl = URLImage.createToStorage(placeHolder, url, url);

        ImageViewer img = new ImageViewer(imgurl.scaled(imgurl.getWidth() * 1, imgurl.getHeight() * 1));

        Label nom = new Label("Nom");
        nom.getAllStyles().setFgColor(0x228B22);
             
                  nom.getUnselectedStyle().setFont(ttfFont);
        Label type = new Label("Type");
        type.getAllStyles().setFgColor(0x228B22);
             
                  type.getUnselectedStyle().setFont(ttfFont);
        Label lieu = new Label("Lieu");
         lieu.getAllStyles().setFgColor(0x228B22);
             
                  lieu.getUnselectedStyle().setFont(ttfFont);
        Label nombreplace = new Label("Nombre de places");
         nombreplace.getAllStyles().setFgColor(0x228B22);
             
                  nombreplace.getUnselectedStyle().setFont(ttfFont);
        Label description = new Label("Description");
        //Label image = new Label("image");
 description.getAllStyles().setFgColor(0x228B22);
             
                  description.getUnselectedStyle().setFont(ttfFont);
        //SpanLabel Message = new SpanLabel("Nom: \n" + f.getNom()+ "\n" + "type AT: " + f.getType()+ "\n" + "Members: " + f.getNbrplace());
        TextField FormationNameField = new TextField(null, "Name");

        FormationNameField.setText(f.getNom());

        TextField nomform = new TextField(null, "nom");
         nomform.getAllStyles().setFgColor(0x000000);
        nomform.setText(f.getNom());
        TextField lieuform = new TextField(null, "lieu");
         lieuform.getAllStyles().setFgColor(0x000000);
        lieuform.setText(String.valueOf(f.getLieu()));
        TextField typeform = new TextField(null, "Type");
         typeform.getAllStyles().setFgColor(0x000000);
        typeform.setText(f.getType());
        TextField nombreplaceform = new TextField(null, "nbrplace");
         nombreplaceform.getAllStyles().setFgColor(0x000000);
        nombreplaceform.setText(String.valueOf(f.getNbrplace()));
        TextField descriptionform = new TextField(null, "description");
          descriptionform.getAllStyles().setFgColor(0x000000);
        descriptionform.setText(f.getDescription());

        Container Container = new Container(new FlowLayout());
      
       Container.addAll(nom, nomform, type, typeform, lieu, lieuform, nombreplace, nombreplaceform, description, descriptionform);
        FormationDetail.add(img);
         Button reserver = new Button("Réserver");
         
           Button stat = new Button("Statistiques");
       
        FormationDetail.add(reserver);
   
        FormationDetail.add(stat);
       
        FormationDetail.add(Container);

        Container ButtonsContainer = new Container(new FlowLayout());

        FormationDetail.add(ButtonsContainer);
        stat.addActionListener(ev -> {
            
            StatFormation(res).show();
            
            
            
            
        });
     
        reserver.addActionListener(ev -> {

            Reservation p = new Reservation();
            FosUser u=new FosUser();
            p.setIdformation(f.getId());
           // p.setIdu(User.getId());
             //  System.out.println("nom" +User.getId()); 
         //   p.setUsername(User.getEmail());
          //  p.setEtat(User.getUsername());
//           p.setAvis(User.getEmail());
            if(f.getNbrplace()>0) {
            if (ServiceFormation.getInstance().AjouterReservation(p)) {
                Dialog.show("Success", "Réservation ajoutée avec succès", new Command("OK"));
                new ListFormation(res).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }
        
            } else {
                Dialog.show("ERROR", "Plus de places disponibles ! A La prochaine", new Command("OK"));
                
            }
        });
        FormationDetail.revalidate();

        FormationDetail.getToolbar().addCommandToLeftBar("", res.getImage("retourner.png"), eve -> {
          ListFormation h=new ListFormation(res);
          h.show();
            });

        return FormationDetail;
    }
        public Form StatFormation(Resources res) {

        FormationPieChart a = new FormationPieChart();
        Form stats_Form = a.execute();
        SpanLabel test_SpanLabel = new SpanLabel("Hiiii");
        Class cls = FormationPieChart.class;
     
           stats_Form.getToolbar().addCommandToLeftBar("", res.getImage("retourner.png"), eve -> {
          ListFormation h=new ListFormation(res);
          h.show();
            });

        return stats_Form;
    }
        

    
    
    

}