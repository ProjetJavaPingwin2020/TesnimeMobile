/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import Entity.Articles_especes;
import Entity.Commentaire;
import static GuiForm.ListFormation.currentForm;
import Services.RefugieService;
import Services.Service_Commentaire;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class CommentaireG extends  BaseForm {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author ASUS
 */

    Form current;
    int idCamp=0;
     public CommentaireG() {
    }
    

    public  CommentaireG(Resources res) {
    
        current=this;
    

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
        current=this;
        setLayout(BoxLayout.y());
      getToolbar().addMaterialCommandToLeftSideMenu("back", 
                FontImage.MATERIAL_BACKUP, ev->{new gererArticle(current).show();});
          getToolbar().addCommandToLeftSideMenu("Exit",
            null, ev->{Display.getInstance().exitApplication();});
          this.getToolbar().addCommandToLeftSideMenu("Ajouter Commentaire", null, ev->{
         new CommentaireG(res).show();
    });
            this.getToolbar().addCommandToLeftSideMenu("Ajouter Article", null, ev->{
         new  refugie(res).show();
    });
        Style catRecStyle= getAllStyles();
      
              setTitle("Ajout des Commentaires ");
        TextField tfnom= new TextField("","Saisir le contenu");
         tfnom.setUIID("TextFieldBlack");
//affichage mte3 liste commentaire win? 
        ComboBox<String> tfcamp=new ComboBox("Liste Article");
        ArrayList<Articles_especes>listCamp
                =Service_Commentaire.getInstance().getAllArticle();
        tfcamp.setUIID("TextFieldBlack");
//        System.out.println("why"+listCamp.get(0).getTitre());

for(int i=0;i<listCamp.size();i++)
{ tfcamp.addItem(listCamp.get(i).getTitre());
System.out.println("why"+listCamp.get(i).getTitre());
}
//affichage

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
         new  gererCommentaire(current).show();
        });
        
        ajouter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfnom.getText().length()==0)||(tfnom.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                { try {
                        for(int i=0;i<listCamp.size();i++)
{if(tfcamp.getSelectedItem().equals(listCamp.get(i).getTitre()))
{idCamp=listCamp.get(i).getId();
                       // System.out.println("hetha houa article hh"+tfcamp.getSelectedItem());

                                       System.out.println("hetha houa article"+listCamp.get(i).getTitre());

break;
}
}
                        Commentaire t = new Commentaire(tfnom.getText(),idCamp);
                        t.setArticle(idCamp);
                       System.out.println("hetha houa article"+idCamp);

                        if( Service_Commentaire.getInstance().addCommentaire(t))
                        { Dialog.show("Success","Connection accepted",new Command("OK"));    
                        }
                           
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "you should choose a article", new Command("OK"));
                    }
                }  
            }
        });
        current.addAll(tfnom,tfcamp,ajouter);
     //   current.addScrollListener((ScrollListener) tfcamp);
        current.show();
    }
}


