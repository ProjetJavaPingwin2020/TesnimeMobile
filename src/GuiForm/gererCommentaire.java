/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;
//itala3 ken heki lo5rin l kol fehom mochkla nhot
import Entity.Articles_especes;
import Entity.Commentaire;
import Services.Service_Commentaire;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import java.util.ArrayList;
import statistiques.EventPieChart;


/**
 *
 * @author ASUS
 */
public class gererCommentaire extends Form{
    Form current;
    int idCamp=0;
    Resources res;

    public Form getCurrent() {
        return current;
    }

    public void setCurrent(Form current) {
        this.current = current;
    }
    
        public gererCommentaire(Form previous){
        
        current = this;
       
              setTitle("Liste Commentaires");
        setLayout(BoxLayout.y());
        
        for (Commentaire w : new Service_Commentaire().getAllCommentaire())
        {
           //mich lezm mela taw netsrf kifh matodhhch wkgw behyy tessnime . me habtech tessnime fama mochkla fil res ken jet current ray 5edmet
            this.add(affichage(w));
        }
        this.show();
       
      
    }
        //alma khali khali taw noke dnaml f pause w nmchi lil page lezmni ngued l video ey behy tess allah ghalbetch

   
        
    public Container affichage(Commentaire c){
       
         /* String url="http://localhost/test1.1/web/front/img/"+c.getImage();//hteheya win khdemt image
        System.out.println(url);
     
         ImageViewer imgv;
     Image imge;
     EncodedImage enc;
    
     enc=EncodedImage.createFromImage(MyApplication.theme.getImage("load-png.png"), false);//maandich round.png gi then m
        imge=URLImage.createToStorage(enc, url, url);

        
        
        
        imgv=new ImageViewer(imge);
        */
           
{  


        Container cn1=new Container(new BorderLayout());
        Container cn2=new Container(BoxLayout.y());
        Button btn=new Button("Contenu: "+c.getContenu());
        //Label lab=new Label("Cotenu:"+c.getContenu());
        Label  det=new Label("Datepub :"+String.valueOf(c.getDatePub()));
        //Label labnbr=new Label("Id :"+String.valueOf(c.getId()));
        Label labn=new Label("Article :"+c.getContenuArticle());
      
       // Label nomCamp=new Label("Nom Commentaire :"+c.getContenu());
       
        cn2.add(btn).add(labn).add(det);
        cn1.add(BorderLayout.WEST, cn2);
        //cn1.add(BorderLayout.CENTER,labnbr);
     //supprimer   
        btn.addActionListener(e->{
            Form f2=new Form("Details",BoxLayout.y());
        
          TextField  tfPrenom=new TextField(c.getContenu());
           tfPrenom.setUIID("TextFieldBlack");
            TextField  tfnom=new TextField(c.getContenuArticle());
             tfnom.setUIID("TextFieldBlack");
          //TextField tfage=new TextField(String.valueOf(c.getAge()));
         //TextField  tforigine=new TextField(c.getOrigine());
         ComboBox<String> tfcamp=new ComboBox("Liste Articles");
                 ArrayList<Articles_especes>listArticles=Service_Commentaire.getInstance().getAllArticle();
          tfcamp.setUIID("TextFieldBlack");      
//warinii wi ajout
for(int i=0;i<listArticles.size();i++)
{ tfcamp.addItem(listArticles.get(i).getTitre());
System.out.println(listArticles.get(i).getTitre());
}
    Button btn_rate=new Button("rating");
     Button btn_modif=new Button("modifier");
     Button btn_sup=new Button("supprimer");
  Button back=new Button("back");
     f2.add("Comentaire").add("Contenu : ").add(tfPrenom).add(btn_sup).add(btn_modif);
     btn_sup.addActionListener(ww ->
     
     {
         new Service_Commentaire().suppCommentaire(c.getId());
         new gererCommentaire(this).showBack();
     });
  
    
          btn_modif.addActionListener(aa ->
     
     {                     for(int i=0;i<listArticles.size();i++)
{ if(tfcamp.getSelectedItem().equals(listArticles.get(i).getContenu()))
{idCamp=listArticles.get(i).getId();
System.out.println(idCamp);

break;
}
    }
       //  c.setArticle(idCamp);
        c.setContenuArticle(tfnom.getText());
         c.setContenu(tfPrenom.getText());
        
//c.setArticle(Integer.valueOf(tfcamp.getSelectedItem()));
         new Service_Commentaire().modifierCommentaire(c);
         new gererCommentaire(this).showBack();
     }//warini guui comm
     
     );
            f2.getToolbar().addCommandToLeftBar("back", null, ev->{
            this.show();
        });
                   f2.show();
        });
        
        cn1.setLeadComponent(btn);
        return cn1;
                
    }

    
        
    
}
    


    
}