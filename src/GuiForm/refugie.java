/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import Entity.Articles_especes;
import Entity.Commentaire;
import static GuiForm.ListFormation.currentForm;
import Services.RefugieService;
import Services.Service_Commentaire;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.datatransfer.DropTarget;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.ScrollListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import java.util.ArrayList;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class refugie extends BaseForm  {
    Form current;
    int idCamp=0;
         String  maChaine="";
         String l="";
         
     public refugie() {
    }
    

    public refugie(Resources res) {
        current=this;
    

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Liste des formations");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
       // tb.addSearchCommand(e -> {
     //   });

        //alma laisse tomber taw ndeco w nodkhl mew kharafwtech kifee dzl tessnime   pgg 
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
        setLayout(BoxLayout.y());
      getToolbar().addMaterialCommandToLeftSideMenu("back", 
                FontImage.MATERIAL_BACKUP, ev->{new gererArticle(current).show();});
         getToolbar().addCommandToLeftSideMenu("Exit",
            null, ev->{Display.getInstance().exitApplication();});
        /*  this.getToolbar().addCommandToLeftSideMenu("Ajouter Commentaire", null, ev->{
         new  Commentaire(current).show();
    });*/
            this.getToolbar().addCommandToLeftSideMenu("Ajouter Article", null, ev->{
         new  refugie(res).show();
    });
        Style catRecStyle= getAllStyles();
       // catRecStyle.setBgColor(0xAACDFC);
        
        //catRecStyle.setBgColor(0xefc2c2);
        setTitle("Ajout des Articles ");
          
        TextField tfnom= new TextField("","Saisir le titre");
         tfnom.setUIID("TextFieldBlack");
        TextField tfprenom=new TextField("","Saisir le contenu");
         tfprenom.setUIID("TextFieldBlack");
                TextField tfNum=new TextField("","Saisir Numlike");
                 tfNum.setUIID("TextFieldBlack");
        //ComboBox<String> tfcamp=new ComboBox("Liste Commentaire");
        //ArrayList<Commentaire>listCamp=RefugieService.getInstance().getAllCamp();
        
       // System.out.println("why"+listCamp.get(0).getNomCamp());

/*for(int i=0;i<listCamp.size();i++)
{ tfcamp.addItem(listCamp.get(i).getContenu());
System.out.println("why"+listCamp.get(i).getContenu());
}*/
//kifeh image mte3 tenregistriha esmha barka wala fip apath kemel? path imen
// cbn bb ?
        Container cnt3=new Container(new FlowLayout(Container.CENTER));

if (DropTarget.isSupported()) {
    DropTarget dnd = DropTarget.create((evt)->{
        String srcFile = (String)evt.getSource();
        System.out.println("Src file is "+srcFile);
       
     maChaine = srcFile;
               //maChaine.replace("http://localhost/jardin1/web/", "");
        System.out.println("Location: "+evt.getX()+", "+evt.getY());
        if (srcFile != null) {
            try {
                Image img = Image.createImage(FileSystemStorage.getInstance().openInputStream(srcFile));
                l= maChaine.substring(19,srcFile.length());
              cnt3.add(img);
                revalidate();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } 
    }, Display.GALLERY_IMAGE);
}


        Button ajouter=new Button("ajouter");
        //style button
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        Style butStyle5=ajouter.getAllStyles();
        butStyle5.setBorder(RoundRectBorder.create().
        strokeColor(0).
        strokeOpacity(120).
        stroke(borderStroke));
        butStyle5.setBgColor(0x096A09);
        butStyle5.setBgTransparency(255);
        butStyle5.setMarginUnit(Style.UNIT_TYPE_DIPS);
        butStyle5.setMargin(Component.BOTTOM, 0);       
        butStyle5.setMargin(Component.TOP,0);           
        butStyle5.setMargin(Component.LEFT,0);  
        butStyle5.setMargin(Component.RIGHT,0); 
        
                    this.getToolbar().addCommandToRightBar("List", null, ev->{
         new  gererArticle(current).show();
        });
        
        ajouter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfnom.getText().length()==0)||(tfnom.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
      
                else
                { 
      
                        Articles_especes t = new Articles_especes(tfnom.getText(),tfprenom.getText());
                        t.setImage(l);
//,Integer.parseInt(String.valueOf(tfcamp))
                        if( RefugieService.getInstance().addRefugie(t))
                        { Dialog.show("Success","Connection accepted",new Command("OK"));
             
                            
                        }
                           
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                   
                    
                }  
            
            }
        });
       
        
        
     

      Service_Commentaire serviceCommentaire=new Service_Commentaire();
ArrayList<Commentaire> listComments= new ArrayList<>();
//listComments=serviceCommentaire.getComments(idBonPlan);

for(Commentaire comment:listComments){
          
            Container commentContainer=new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
          //  Stroke borderStroke = new Stroke(10, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
            RoundBorder border=RoundBorder.create().color(0x99CCCC).strokeColor(5).
            strokeOpacity(120).stroke(borderStroke).rectangle(true);

            Container authorDateContainer=new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
            
            Label dateLabel=new Label();
           // dateLabel.setText(comment.getDateCreation().substring(0,comment.getDateCreation().length()-3));
            Container dateContainer=new Container(BoxLayout.x());
            dateContainer.add(dateLabel);
            authorDateContainer.add(BorderLayout.NORTH,dateContainer);
            
           // Utilisateur auteur=serviceCommentaire.getAuteurById(comment.getIdAuteur());
            //placeholder=EncodedImage.create("/testos.png");
           // String url="http://localhost/bons_plans/image/"+auteur.getPhotoDeProfil();
          //  img=URLImage.createToStorage(placeholder, url,url, URLImage.RESIZE_SCALE);
          //  photoProfilViewer=new ImageViewer(img);
            Container photoContainer=new Container(BoxLayout.x());
         //   photoContainer.add(photoProfilViewer);
            authorDateContainer.add(BorderLayout.WEST,photoContainer);
            
            Label auteurLabel=new Label();
           // auteurLabel.setText(auteur.getUsername());
            Container auteurContainer=new Container(BoxLayout.x());
            auteurContainer.add(auteurLabel);
            authorDateContainer.add(BorderLayout.SOUTH,auteurContainer);
            
   
            SpanLabel avisLabel=new SpanLabel();
            avisLabel.setText(comment.getContenu());
            Container avisContainer= new Container(BoxLayout.x());
            avisContainer.add(avisLabel);
            
            commentContainer.addComponent(BorderLayout.WEST,authorDateContainer);
//            avisContainer.setX(1);
            commentContainer.addComponent(BorderLayout.CENTER,avisContainer);

            if(comment.getUser()==1){
                Button supprimer=new Button("Supprimer");
                commentContainer.add(BorderLayout.SOUTH,supprimer);
                supprimer.addActionListener((e) -> {
                    if(Dialog.show("Valider", "Voulez vous vraiment supprimer ce commentaire ? ", "Confirmer", "Annuler"))
                    {
                        //                            serviceCommentaire.supprimerCommentaire(comment.getId(), request);
                        refugie ref= new refugie();
                        ref.getF().show();
                    }
                    
        });
            }
            
            //commentContainer.getAllStyles().setBorder(border);
           
            
        }  
     current.addAll(tfnom,tfprenom,tfNum,cnt3,ajouter);
     //   current.addScrollListener((ScrollListener) tfcamp);
                
        current.show();
    }

    public Form getF() {
        return current;
    }

    public void setF(Form current) {
        this.current =current;
    }
   
    
}
