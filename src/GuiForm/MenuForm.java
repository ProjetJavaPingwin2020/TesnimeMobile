/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import com.codename1.components.ScaleImageLabel;
import com.codename1.l10n.ParseException;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
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

/**
 *
 * @author asus
 */
public class MenuForm extends BaseForm{
     Button btnacc,btnevent, btnboutique, btnespeces, btnformation, btninformation;
   private Form current;

    public MenuForm(Resources res) {
        super("", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Welcome ");
        getContentPane().setScrollVisible(false);

      
        btnboutique = new Button("Boutique");
        btnespeces= new Button("Espèces");
        btnevent = new Button("Evénements");
        btnformation = new Button("Formations");
        btninformation = new Button("Informations");

        super.addSideMenu(res);

        tb.addSearchCommand(e -> {
        });

        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 4) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 4);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(3, FlowLayout.encloseCenter(new Label(res.getImage(""), "PictureWhiteBackgrond")))
                )
        ));
        btnevent.addActionListener(e-> {
           new EvenementMenuForm(res).show();
        });
        
             btnformation.addActionListener(e-> {
           new ListFormation(res).show();
        });
             btnboutique.addActionListener(e-> {
           try {
                new ProduitFront(current).show();
            } catch (ParseException ex) {
               
            }
        });
 btninformation.addActionListener(e-> {
     new refugie(res).show();
        });
        Container hi = new Container(new GridLayout(3, 2));
                add(btnboutique).
                add(btnespeces).
                add(btnevent).
                add(btnformation).
                add(btninformation);
        addStringValue("", hi);

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
       // add(createLineSeparator(0xeeeeee));
    }
}

    

