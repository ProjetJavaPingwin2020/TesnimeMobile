/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import Entity.Articles_especes;
import Entity.Commentaire;
import Services.RefugieService;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.mycompany.myapp.MyApplication;
import static com.mycompany.myapp.MyApplication.res;

import java.util.ArrayList;


import java.io.IOException;
import statistiques.EventPieChart;



/**
 *
 * @author ASUS
 */
public class gererArticle extends BaseForm{
    Form current;
    int idCamp=0;
     ImageViewer iv;
    Image i;
    Image istar;
    ImageViewer ivstar;
     Articles_especes CurrentEvent;
       int current_eid;
       ArrayList<Articles_especes> QuestionS = new ArrayList();

    public Form getCurrent() {
        return current;
    }

    public void setCurrent(Form current) {
        this.current = current;
    }
    
        public gererArticle(Form previous){
     
        
        current = this;
       
              setTitle("Liste des Articles");
        setLayout(BoxLayout.y());
        
        for (Articles_especes w : new RefugieService().getAllRefugie())
        {
           
            this.add(affichage(w));
        }
        this.show();
         this.getToolbar().addCommandToRightBar("Stat", MyApplication.theme.getImage("back-command.png"), ev->{
     StatEvent(previous).show();  
        });
       //aya ciao 
         this.getToolbar().addMaterialCommandToLeftBar("back", 
                FontImage.MATERIAL_BACKUP, ev->{new gererCommentaire(current).show();});
    }

   //chisssemha l page d aceeuil ?
        
        
    public Container affichage(Articles_especes c){
       //amali dossier Articles especes
          String url="http://localhost/integration/test1.1/web/Articles_especes/"+c.getImage();//hteheya win khdemt image
        System.out.println(url);
     
         ImageViewer imgv;
     Image imge;
     EncodedImage enc;
    
     enc=EncodedImage.createFromImage(MyApplication.theme.getImage("mer.jpg"), false);//maandich round.png gi then m
        imge=URLImage.createToStorage(enc, url, url);

        
        
        
        int current_eid = c.getId();
        Form hi2 = new Form(c.getTitre(), BoxLayout.y());
        SpanLabel sl = new SpanLabel(c.getContenu());
        Container co1;
        
        
        
        
        imgv=new ImageViewer(imge);
        
        Container cn1=new Container(new BorderLayout());
        Container cn2=new Container(BoxLayout.y());
        Button btnback=new Button("back");
        Button btn=new Button("Titre : "+c.getTitre());
        Label lab=new Label("Cotenu"+c.getContenu());
           Label lab1=new Label("Datepub"+c.getDatepub());
        Label labnbr=new Label("Id :"+String.valueOf(c.getId()));
        Label num=new Label("Numlike :"+String.valueOf(c.getNumlike()));
  Label nomCamp=new Label("Nom Commentaire :"+c.getContenu());
    
        cn2.add(btn).add(lab).add(imgv).add(lab1).add(num);
        cn1.add(BorderLayout.WEST, cn2);
        //cn1.add(BorderLayout.CENTER,labnbr);
     //supprimer   
      
        btn.addActionListener(e->{
            Form f2=new Form("Details",BoxLayout.y());
          TextField  tfNom=new TextField(c.getTitre());
            tfNom.setUIID("TextFieldBlack");
          TextField  tfPrenom=new TextField(c.getContenu());
           tfPrenom.setUIID("TextFieldBlack");
            TextField  tfnum=new TextField(c.getNumlike());
             tfnum.setUIID("TextFieldBlack");
          //TextField tfage=new TextField(String.valueOf(c.getAge()));
         //TextField  tforigine=new TextField(c.getOrigine());
         ComboBox<String> tfcamp=new ComboBox("Liste Commentaire");
                 ArrayList<Commentaire>listCamp=RefugieService.getInstance().getAllCamp();
//warinii wi ajout
for(int i=0;i<listCamp.size();i++)
{ tfcamp.addItem(listCamp.get(i).getContenu());
System.out.println(listCamp.get(i).getContenu());
}
    Button btn_rate=new Button("rating");
         Button btn_stat=new Button("stat");
     Button btn_modif=new Button("modifier");
     Button btn_sup=new Button("supprimer");
     f2.add("Articles_especes").add("Titre: ").add(tfNom).add("Contenu : ").add(tfPrenom).add("Numlike").add(tfnum).add(btn_sup).add(btn_modif).add(btn_stat);
     btn_sup.addActionListener(ww ->
     
     {//issmani imen fi hethi raw me andich jointure juste naamel fi modif ala table b yaani5 oss oss fhmet mnech ghalta oskoy
         new RefugieService().suppRefugie(c.getId());
         new gererArticle(this).showBack();
     }
     
     );
    
     
          btn_modif.addActionListener(aa ->
     //hethi tetfassa5
     {                     for(int i=0;i<listCamp.size();i++)
{ if(tfcamp.getSelectedItem().equals(listCamp.get(i).getContenu()))
{idCamp=listCamp.get(i).getId();
System.out.println(idCamp);

break;
}
    }
         c.setTitre(tfNom.getText());
      
         c.setContenu(tfPrenom.getText());
    
        System.out.println("hetah id "+c.getId());
//         c.setIdcamp(Integer.valueOf(tfcamp.getText()));
         new RefugieService().modifierRefugie(c);
         new gererArticle(this).showBack();
     }
     
     );
            f2.getToolbar().addCommandToLeftBar("back", null, ev->{
            this.show();
        });
                   f2.show();
        });
        
        cn1.setLeadComponent(btn);
        return cn1;
                
    }

    
      public Form StatEvent(Form previous) {
        EventPieChart a = new EventPieChart();
        Form stats_Form = a.execute();
        SpanLabel test_SpanLabel = new SpanLabel("Hiiii");
        Class cls = EventPieChart.class;
        stats_Form.getToolbar().addCommandToRightBar("back", null, evt -> {
            new gererArticle(previous).show();
        });
        return stats_Form;
        }
    
}
