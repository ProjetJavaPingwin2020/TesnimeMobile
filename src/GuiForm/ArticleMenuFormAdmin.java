package GuiForm;


import GuiForm.BaseForm;
import GuiForm.ShowFormations;
import GuiForm.addFormationForm;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

public class ArticleMenuFormAdmin extends BaseForm{
     
   Button  btncomment,btnaff,btnajout,btnstat;
    String path;
  
    public ArticleMenuFormAdmin(Resources res) {
        super("", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Espace Article");
        getContentPane().setScrollVisible(false);
        

     
        btnaff= new Button("Toutes les Articles");
        btncomment= new Button("Gestion Commentaires");
        btnajout= new Button("Ajouter un Article");
       // btnstat= new Button("Les types les plus utilisés");
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
           new refugie(res).show();  
        });
        btncomment.addActionListener((e)->{
           new CommentaireG(res).show();  
        });
    
    btnajout.addActionListener(e -> {
       new  refugie(res).show();
        });
 
        
        addStringValue("", btnaff);
      
        addStringValue("", btnajout);
 
        
    
}    

   private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
