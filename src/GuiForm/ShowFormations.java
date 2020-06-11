/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import Entity.Formateur;
import Entity.Formation;
import static GuiForm.ListFormation.currentForm;
import static GuiForm.SignInForm.f;
import Services.ServiceFormation;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.facebook.ui.LikeButton;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
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
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author asus
 */
public class ShowFormations extends BaseForm {

    public static Form f, form;
    public static SpanLabel lb;
    public static Label TitleShow;
     ComboBox<String> combo = new ComboBox<>();
    public static String url = "http://localhost/integration/test1.1/web/imageFormations";
    public static Button suppbtn, modifbtn, btnchercher, btnstat, loc;
    public static TextField titrecherche;
    public static Component cnt;
    public static Formation Bps = new Formation();

    public static Font ttfFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_LARGE);

    public ShowFormations(Resources res) {

        super("", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Liste des Formations");
        getContentPane().setScrollVisible(false);

        System.out.println("heloooooooooooo");
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

        ServiceFormation SF = new ServiceFormation();

        ArrayList<Formation> lis = SF.getAllFormations();
        f = new Form(BoxLayout.y());

        for (Formation li : lis) {

            System.out.println("owel el for");
            Label nom = new Label("Nom  : " + li.getNom());
            nom.getAllStyles().setFgColor(0x000000);
            //Label date = new Label("Date   :  " +SP.getDateS());
            Label type = new Label("Type  : " + li.getType());
            type.getAllStyles().setFgColor(0x000000);
            Label lieu = new Label("Lieu  : " + li.getLieu());
            lieu.getAllStyles().setFgColor(0x000000);
            Label desc = new Label("Description : " + li.getDescription());
            desc.getAllStyles().setFgColor(0x000000);
            //  Label heure = new Label("Heure : " + li.getHeure());
            Label detailsEv = new Label("Voir Details ");
            detailsEv.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
            Container cc = new Container(BoxLayout.x());
            Container c = new Container(BoxLayout.y());
            Container k = new Container(BoxLayout.x());
            suppbtn = new Button("Supprimer");
            modifbtn = new Button("Modifier");

            Image placeholder = Image.createImage(600, 600);
            EncodedImage enc = EncodedImage.createFromImage(placeholder, false);
            URLImage urlim = URLImage.createToStorage(enc, li.getNomImage(), url + "/" + li.getNomImage());
            System.out.println(url + "/" + li.getNomImage());
            System.out.println(urlim);
            ImageViewer imgV = new ImageViewer();
            imgV.setImage(urlim);

            c.add(nom);
            c.add(type);
            c.add(detailsEv);

            c.getStyle().setPaddingLeft(200);
            c.getStyle().setPaddingTop(200);
            k.addAll(imgV, c);
            k.setSameWidth();
            k.setSameHeight();
            //f.add(c);
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            add(k);

            detailsEv.addPointerPressedListener((l) -> {

                form = new Form(BoxLayout.y());
                Label nomf = new Label();
                nomf.getAllStyles().setFgColor(0x228B22);

                nomf.getUnselectedStyle().setFont(ttfFont);
                Label typef = new Label();
                typef.getAllStyles().setFgColor(0x228B22);
                typef.getUnselectedStyle().setFont(ttfFont);

                Label lieuf = new Label();
                lieuf.getAllStyles().setFgColor(0x228B22);
                lieuf.getUnselectedStyle().setFont(ttfFont);
                Label nbrf = new Label();
                nbrf.getAllStyles().setFgColor(0x228B22);
                nbrf.getUnselectedStyle().setFont(ttfFont);
                Label descf = new Label();
                descf.getAllStyles().setFgColor(0x228B22);
                descf.getUnselectedStyle().setFont(ttfFont);
                Label formateur = new Label();
                formateur.getAllStyles().setFgColor(0x228B22);
                formateur.getUnselectedStyle().setFont(ttfFont);

                Container F3 = new Container(BoxLayout.y());

                F3.add(nomf);
                F3.add(typef);
                F3.add(lieuf);
                F3.add(nbrf);
                F3.add(descf);
               F3.add(formateur);

                System.out.println("imaage");

                EncodedImage en = EncodedImage.createFromImage(placeholder, false);
                URLImage urli = URLImage.createToStorage(en, url + "/" + li.getNomImage(), url + "/" + li.getNomImage());
                ImageViewer img1 = new ImageViewer();
                img1.setImage(urli);

                F3.add(img1);

                ConnectionRequest con = new ConnectionRequest();
                ConnectionRequest con2 = new ConnectionRequest();

                String url = "http://localhost/integration/test1.1/web/app_dev.php/mobile/findFormation/" + li.getId();
                con.setUrl(url);

                con.addResponseListener((le) -> {
                     String nomformateur = combo.getSelectedItem();
                    String reponse = new String(con.getResponseData());
                    System.out.println(reponse);
                    nomf.setText("Nom   :   " + li.getNom());

                    typef.setText("Type  :   " + li.getType());
                    descf.setText("Description  :  " + li.getDescription());
                    lieuf.setText("Lieu  :   " + li.getLieu());
                    nbrf.setText("Nombre de places  :   " + li.getNbrplace());
                   formateur.setText("Formateur : " + ((li.getNomformateur())));

                    suppbtn.addActionListener((eeee) -> {

                        if (Dialog.show("Confirmation", "Voulez-vous vraiment supprimer cette formation ? ", "OK", "ANNULER")) {
                            String result = ServiceFormation.getInstance().DeleteFormation(li);
                            if (!result.equals("Error")) {
                                Dialog.show("Success", result, new Command("OK"));
                                new ShowFormations(res).show();
                            } else {
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                            }
                        } else {

                        }
                    });
                    modifbtn.addActionListener((ej) -> {

                        UpdateFormationForm h = new UpdateFormationForm(li, res);
                        h.getF().show();

                    });

                    F3.add(suppbtn);
                    F3.add(modifbtn);

                });

                NetworkManager.getInstance().addToQueueAndWait(con);

                System.out.println("test el F3");

                form.add(F3);
                form.getToolbar().addCommandToLeftBar("", res.getImage("retourner.png"), eve -> {
                    ShowFormations h = new ShowFormations(res);
                    h.show();
                });

                form.show();

            });

            System.out.println("test ba3d a");

        }
   //     lb.setText(lis.toString());

        //f.show();
    }

    public Form StatFormation(Resources res) {

        FormationPieChart a = new FormationPieChart();
        Form stats_Form = a.execute();
        SpanLabel test_SpanLabel = new SpanLabel("Hiiii");
        Class cls = FormationPieChart.class;
        stats_Form.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new ShowFormations(res).show();
        });

        return stats_Form;
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
