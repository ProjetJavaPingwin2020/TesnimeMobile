/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import Entity.Formation;
import static GuiForm.ShowFormations.ttfFont;
import Services.ServiceFormation;
import static com.codename1.charts.util.ColorUtil.CYAN;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author asus
 */
public class UpdateFormationForm {
    Form f;
    TextField ttitre,tdescription,tlieu,ttype,theure,tnbrplace;
   Label ttitre1,tdescription1,tlieu1,ttype1,theure1,tnbrplace1;
    Button btnMod,btnAnnuler;
      public static Label TitleShow,fnom;
        public  UpdateFormationForm( Formation ta,Resources res) {
        f = new Form("Modification",BoxLayout.y());
         Container c=new Container(BoxLayout.x());
          
          TitleShow =new Label();
        TitleShow.getAllStyles().setFgColor(0xE30304);
        TitleShow.getUnselectedStyle().setFont(ttfFont);
      
        TitleShow.setText("Détails de la fomation");
        c.add(TitleShow);   
          Container ff=new Container(BoxLayout.x());
          fnom =new Label();
        fnom.getAllStyles().setFgColor(0xE30304);
        fnom.getUnselectedStyle().setFont(ttfFont);
      
        fnom.setText(ta.getNom());
        ff.add(fnom);   
         Container c1=new Container(BoxLayout.x());
        ttitre1=new Label("Nom:");
        ttitre = new TextField(ta.getNom());
        ttitre.getAllStyles().setFgColor(0x000000);
        ttitre1.getAllStyles().setFgColor(0x228B22);
        ttitre1.getUnselectedStyle().setFont(ttfFont);
         c1.addAll(ttitre1,ttitre);
         Container c2=new Container(BoxLayout.x());
         tdescription1=new Label("Description:");
        tdescription = new TextField(ta.getDescription());
        tdescription.getAllStyles().setFgColor(0x000000);
        tdescription1.getAllStyles().setFgColor(0x228B22);
        tdescription1.getUnselectedStyle().setFont(ttfFont);
        c2.addAll(tdescription1,tdescription);
        Container c3=new Container(BoxLayout.x());
         ttype1=new Label("Type:");
        ttype = new TextField(ta.getType());
        ttype.getAllStyles().setFgColor(0x000000);
        ttype1.getAllStyles().setFgColor(0x228B22);
        ttype1.getUnselectedStyle().setFont(ttfFont);
        c3.addAll(ttype1,ttype);
         Container c4=new Container(BoxLayout.x());
         tlieu1=new Label("Lieu:");
        tlieu = new TextField(ta.getLieu());
        tlieu.getAllStyles().setFgColor(0x000000);
         tlieu1.getAllStyles().setFgColor(0x228B22);
        tlieu1.getUnselectedStyle().setFont(ttfFont);
        c4.addAll(tlieu1,tlieu);
        //  Container c5=new Container(BoxLayout.x());
     //   theure1=new Label("Heure:");
       // theure = new TextField(ta.getHeure());
        //theure.setHint("Heure:");
        //theure.getAllStyles().setFgColor(0x000000);
        // theure1.getAllStyles().setFgColor(0xA83839);
    //     c5.addAll(theure1,theure);
          
        btnMod = new Button("Modifier");
        btnAnnuler=new Button("Annuler");
        f.addAll(c,ff,c1,c2,c3,c4);    
        
        f.add(btnMod);
        f.add(btnAnnuler);
        btnMod.addActionListener((e) -> {
            ServiceFormation ser = new ServiceFormation();
            ta.setNom(ttitre.getText());
            ta.setDescription(tdescription.getText());
            ta.setType(ttype.getText());
            ta.setLieu(tlieu.getText());
//            ta.setHeure(theure.getText());
      //     ta.setNbrplace(tnbrplace.getText());
         
 

            System.out.println("9bal modif");
            
            ser.modifierFormation(ta,res);
            
            System.out.println("baad l modif");

            

        }); 
        
        btnAnnuler.addActionListener((e)->{
       ShowFormations a=new ShowFormations(res);
        a.show();
        });
        f.show();
        
    }
    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

    public TextField getTnom() {
        return ttitre;
    }

    public void setTnom(TextField ttitre) {
        this.ttitre = ttitre;
    }    
}
