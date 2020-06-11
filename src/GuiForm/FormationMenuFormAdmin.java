/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;


import static GuiForm.SignInForm.f;
import static GuiForm.SignInForm.theme;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button; 
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

/**
 *
 * @author asus
 */
public class FormationMenuFormAdmin extends BaseForm{
     
   Button btnaff,btnajout,btnafftri;
    String path;
  
    public FormationMenuFormAdmin(Resources res) {
        super("", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Espace Formation");
        getContentPane().setScrollVisible(false);
        

     
        btnaff= new Button("Toutes les formations");
       
        btnajout= new Button("Ajouter une formation");
     
        btnafftri = new Button ("Affichage par gouvernement");
        super.addSideMenu(res);
        
        tb.addSearchCommand(e -> {});
  
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 5) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 5);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(3,FlowLayout.encloseCenter(new Label(res.getImage(""), "PictureWhiteBackgrond"))     )
                )
        ));

                   
      
        btnaff.addActionListener((e)->{
           new ShowFormations(res).show();  
        });
        
    
    btnajout.addActionListener(e -> {
         new addFormationForm(res).show();
        });
    Button btnavis = new Button ("Consulter page Facebook");
   btnavis.addActionListener(e -> {
         new FacebookForm(res).show();
        });
         
   
  
        addStringValue("", btnaff);
      
        addStringValue("", btnajout);
        addStringValue("", btnavis);
       
 
        
    
}    

   private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
