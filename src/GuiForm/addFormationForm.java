/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import Entity.Formateur;
import Entity.Formation;
import static GuiForm.SignInForm.res;
import Services.ServiceFormateur;
import Services.ServiceFormation;
import Services.UserService;

import static com.codename1.charts.util.ColorUtil.CYAN;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
//import java.util.regex.Matcher;
import rest.file.uploader.tn.FileUploader;

/**
 *
 * @author asus
 */
public class addFormationForm extends BaseForm {

    public static TextField nom, type, lieu, description, heure, nomImage, picker, nbrplace;
    public static Picker dPicker;
    Form f;
    String path;
    Button btnajout, imgBtn;
    ComboBox<String> combo = new ComboBox<>();
    ServiceFormation sf = new ServiceFormation();

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

    public addFormationForm(Resources res) {
        super("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Formation");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 5) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 5);
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

        Validator v = new Validator();
        Validator v2 = new Validator();
        Validator v3 = new Validator();
        f = new Form("Ajouter une formation");
        nomImage = new TextField();
        nom = new TextField("", "Nom", 20, TextField.ANY);
        nom.getAllStyles().setFgColor(0x000000);
        v.addConstraint(nom, new LengthConstraint(4));
        type = new TextField("", "Type", 20, TextField.EMAILADDR);
        type.getAllStyles().setFgColor(0x000000);
        v.addConstraint(type, new LengthConstraint(4));
        picker = new TextField();
        dPicker = new Picker();
        dPicker.getAllStyles().setFgColor(0x000000);
        lieu = new TextField("", "Lieu", 20, TextField.ANY);
        lieu.getAllStyles().setFgColor(0x000000);
        v.addConstraint(lieu, new LengthConstraint(4));
        description = new TextField("", "Description", 20, TextField.ANY);
        description.getAllStyles().setFgColor(0x000000);
        v.addConstraint(description, new LengthConstraint(4));

        // heure = new TextField("", "Heure", 20, TextField.ANY);
        //heure.getAllStyles().setFgColor(0x000000);
        nbrplace = new TextField("", "Nombre de places", 20, TextField.NUMERIC);
        nbrplace.getAllStyles().setFgColor(0x000000);

        for (Formateur ff : ServiceFormateur.getInstance().getAllFormateurs()) {

            combo.addItem(ff.getNom());
        }
        combo.getAllStyles().setFgColor(0x000000);

        btnajout = new Button("Ajouter");

        imgBtn = new Button("Parcourir image");
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String A = combo.getSelectedItem().toString();

            }
        });
        imgBtn.addActionListener(e -> {
            Display.getInstance().openGallery(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    if (ev != null && ev.getSource() != null) {
                        path = (String) ev.getSource();
                        System.out.println(path.substring(7));
                        Image img = null;
                        nomImage.setText(path.substring(7));//image heya just label nsob feha fel path
                        System.out.println(nomImage);
                        try {
                            img = Image.createImage(FileSystemStorage.getInstance().openInputStream(path));
                            System.out.println(img);

                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }, Display.GALLERY_IMAGE);
        });
/////////////////////////////////////////////////////
      /*  try {
         btnajout.setIcon(Image.createImage("/mark.png").scaled(30, 30));

         } catch (IOException ex) {
         }
         */
        addStringValue("", nom);
        addStringValue("", type);
        addStringValue("", dPicker);
        addStringValue("", lieu);
        addStringValue("", description);
        addStringValue("", combo);
        addStringValue("", nbrplace);
        addStringValue("", imgBtn);
        addStringValue("", btnajout);

        btnajout.addActionListener((e) -> {
            Formation f = new Formation();
            Formateur ff = new Formateur();
            ServiceFormation ser = new ServiceFormation();

            FileUploader fc = new FileUploader("localhost/integration/test1.1/web/imageFormations");
            System.out.println("owel el try");
            Date dat = new Date();
            System.out.println("dPicker.getDate()" + dPicker.getDate());
            System.out.println("dat.getTime()" + dat.getTime());
            int d = (int) (dPicker.getDate().getTime() - dat.getTime());
            System.out.println("hethiiiiii" + d);

            if (d <= 0) {
                Dialog.show("erreur", "veuillez entrer une date valide supérieure ou égale à la date actuelle", "Ok", null);
            }

            if ((nom.getText().length() == 0) || (type.getText().length() == 0) || lieu.getText().length() == 0
                    || description.getText().length() == 0 || (nbrplace.getText().length() == 0) || (nomImage.getText().length() == 0)) {
                Dialog.show("Erreur", "Champs vide ! Veuillez remplir tous les champs", "ok", null);
            }

            if (d > 0) {
                try {
                    System.out.println("owel el try");
                    String file = fc.upload(nomImage.getText());
                    nomImage.setText(file);

                    System.out.println("ba3d el upload ma3 : " + file);

                } catch (Exception ex) {
                }

                String nomformateur = combo.getSelectedItem();
                System.out.println("nom" + nomformateur);
                int formateur = findformateur(nomformateur);
                System.out.println("idffff" + formateur);
                f.setId(formateur);
                try {
                    f = new Formation(nom.getText(), type.getText(), lieu.getText(), description.getText(), Integer.parseInt(nbrplace.getText()), nomImage.getText(), formateur);

                    if (!nom.getText().isEmpty() && !type.getText().isEmpty() && !lieu.getText().isEmpty() && !description.getText().isEmpty() && !nbrplace.getText().isEmpty() && !combo.getSelectedItem().isEmpty()) {
                     
                        if (Integer.parseInt(nbrplace.getText()) >= 0) {
                            ser.ajoutFormation(f);
                            Dialog.show("Succès", "Formation ajoutée avec succès !", "ok", null);
                            new ShowFormations(res).show();
                        }
                      
                    } else {
                        Dialog.show("Alert", "Champs vide ! Veuillez remplir tous les champs", "ok", null);
                    }
                    

                } catch (NumberFormatException ex) {
                    Dialog.show("Erreur", "Le nombre de places doit etre un entier positif", new Command("OK"));
                }

            }

        });

        dPicker.addActionListener(ev -> {
            picker.setText(pickerToString(dPicker));
            System.out.println(picker.getText());
        });

    }

    public static String pickerToString(Picker p) {

        Date s = p.getDate(); //dPicker howa l picker
        //System.out.println(s);
        Calendar c = Calendar.getInstance();
        c.setTime(s);
        String m1 = Integer.toString(c.get(Calendar.MONTH) + 1);
        String y1 = Integer.toString(c.get(Calendar.YEAR));
        String d1 = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String date = y1 + "-" + m1 + "-" + d1;
        return date;
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    int findformateur(String nomff) {
        int idf = 1;

        for (Formateur c : ServiceFormateur.getInstance().getAllFormateurs()) {
            if (c.getNom().equals(nomff)) {
                idf = c.getId();
            }

        }
        return idf;
    }
}
