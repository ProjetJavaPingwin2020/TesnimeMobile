/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import com.codename1.components.Accordion;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.addNetworkErrorListener;
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
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.ScrollListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;

import Entity.Ratingp;

import Services.ServiceBoutique;
import static GuiForm.ProduitFront.theme;
import java.io.IOException;
import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 *
 * @author bhk
 */
public class RatingAdmin extends BaseForm{
     

        private Form current;
              ServiceBoutique serviceTask = new ServiceBoutique();
            ArrayList<Ratingp> lis = serviceTask.consulterRating(); //liste des Produits
            Container cCenter = new Container();
            
                     
    public RatingAdmin(Form previous) throws ParseException {
        super("", BoxLayout.y());
          Toolbar toolBar = getToolbar();
           setToolbar(toolBar);
             getTitleArea().setUIID("Container");
        setTitle("Votre Panier");
        
        getContentPane().setScrollVisible(false);
         

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
    

          super.addSideMenu(theme);
            
         theme = UIManager.initFirstTheme("/theme");
        
        toolBar.addSearchCommand(e -> {});
     

        
       
        
       
        
            
     
  
        Image img = theme.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 5) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 5);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(3,FlowLayout.encloseCenter(new Label(theme.getImage(""), "PictureWhiteBackgrond"))     )
                )
        ));
        
       
     
    
                
                
        Button btnClose = new Button("Consulter les Commandes");
        btnClose.setIcon(
            FontImage.createMaterial(
                FontImage.MATERIAL_EXIT_TO_APP,
                UIManager.getInstance().getComponentStyle("Button")
            )            
        );
        btnClose.addActionListener
                ((ActionListener<ActionEvent>) (ActionEvent evt) -> {
                    
                    
             try {
                 CommandeAdmin h = new CommandeAdmin(current);
                 h.show();
             } catch (ParseException ex) {
                 
             }
                    
        });

        setLayout(new BorderLayout());
        addComponent(BorderLayout.CENTER, cCenter);
        addComponent(BorderLayout.SOUTH, btnClose);
       
        populateScreen(cCenter);
  }

   
  
  private void populateScreen(Container cnt) throws ParseException {
       cnt.removeAll();
       cnt.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
       cnt.setScrollableY(true);
     
       for(Ratingp c : lis) {
           cnt.addComponent(new AccrClient4(c));
       }
       cnt.repaint();
    }
  
    }
 class AccrClient4 extends Accordion {
     
      private Resources theme=UIManager.initFirstTheme("/theme");

        Ratingp c;
        MultiButton mb;
         Container IMG = new Container();
          Container c2 = new Container();
          
       Container c3 = new Container();
        public AccrClient4(Ratingp c) throws ParseException {
            super();
            c3.setScrollableY(true);
            c2.setScrollableY(true);
            this.c = c;
            this.setScrollableY(true);
            addContent(
                this.c2 = createHeader(c), createDetail(c)
            );
        }
        
        public Ratingp getClient() {
            return this.c;
        } 
        
       
        private Container createHeader(Ratingp c) {
       
             
            IMG.setPreferredSize(new Dimension(300, 300));
             
            EncodedImage placeholder=EncodedImage.createFromImage(theme.getImage("200.gif"),true);
                                
                
   
                                      
                                      
            
               Label s = new Label("Evaluation de: "+c.getNom_user());
                Label s1 = new Label("Le Produit: "+c.getProduct());
                s.getAllStyles().setFgColor(0xf20d0d) ;
           
            c2.add(s);
            c2.add(s1);
            
           return  c2;
                                       //add(img);

   
                
           
          
          
          //return mbt;
            
            
        }
        
        private Container createDetail(Ratingp c) throws ParseException {
         
  
                  
                      
            Container cDetail = BorderLayout.center(
                TableLayout.encloseIn(
                        2,
                        true,
               
                        new Label("Degré de satisfaction:        "),
                       new Label("" +c.getDegre()),
                       new Label("Commentaire:        "),
                       new Label("" +c.getCommentaire())
                       
                        
                      
                   
                        
                    
                )            
            );
            
            cDetail.setScrollableY(true);
            //Commande
            
        
            
            
            
            
           
        
            
            
            
            
  
            
        
            return cDetail;
        }
        
      
        
        public boolean isInteger(String s) {
            try { 
                Integer.parseInt(s); 
            } catch(NumberFormatException | NullPointerException e) { 
                return false; 
            }
            // only got here if we didn't return false
            return true;
        }        

      
    
 }   
        
                
  //////////////////scroll/////////////
        

        
        