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
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.addNetworkErrorListener;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
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
import Entity.Commande;
import Entity.Produit;
import Entity.login;
import Services.ServiceBoutique;
import static GuiForm.ProduitFront.theme;
import java.util.ArrayList;


/**
 *
 * @author ASUS
 */
public class PanierForm extends BaseForm{
   
     ServiceBoutique serviceTask = new ServiceBoutique();
            ArrayList<Commande> lis = serviceTask.consultercommande(); //liste des Produits
            Container cCenter = new Container();
             private Form current;
                     
    public PanierForm(Form previous) throws ParseException {
        
        
      super("", BoxLayout.y());
          Toolbar toolBar = getToolbar();
           setToolbar(toolBar);
             getTitleArea().setUIID("Container");
        setTitle("Votre Panier");
        
        getContentPane().setScrollVisible(false);
         super.addSideMenu(theme);

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
        
       
     
    
       
                
        Button btnClose = new Button("Retour aux Produits");
        btnClose.setIcon(
            FontImage.createMaterial(
                FontImage.MATERIAL_EXIT_TO_APP,
                UIManager.getInstance().getComponentStyle("Button")
            )            
        );
        btnClose.addActionListener
                ((ActionListener<ActionEvent>) (ActionEvent evt) -> {
                    
          try {
              ProduitFront h = new ProduitFront(current);
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
     
       for(Commande c : lis) {
           cnt.addComponent(new AccrClient1(c));
           cnt.setScrollableY(true);
       }
       cnt.repaint();
    }
  
    }
 class AccrClient1 extends Accordion {
      ServiceBoutique serviceTask = new ServiceBoutique();
      private Resources theme=UIManager.initFirstTheme("/theme");

        Commande cp;
        
        MultiButton mb;
         Container IMG = new Container();
          Container c2 = new Container();
          
            TextField tfquantitecmd;
          
       Container c3 = new Container();
        public AccrClient1(Commande cp) throws ParseException {
            super();
            this.cp = cp;
            addContent(
                this.c2 = createHeader(cp), createDetail(cp)
            );
        }
        
        public Commande getClient() {
            return this.cp;
        } 
        
        private Container createHeader(Commande cp) {
       
           login l = new login();
ServiceBoutique s1= new ServiceBoutique();
l=s1.login(); //traja3l
       
           
           Label s = new Label("Commande de "+String.valueOf(cp.getQuantite())+" unités de "+cp.getNom_prod());
                s.getAllStyles().setFgColor(0xf20d0d) ;
             

           
          
          c2.add(s);
        
            
           return  c2;
          //return mbt;
            
            
        }
        
        private Container createDetail(Commande cp) throws ParseException {
         
  
                     
                      
            Container cDetail = BorderLayout.center(
                TableLayout.encloseIn(
                        2,
                        true,
               
                        new Label("Produit:        "),
                       new Label("" +cp.getNom_prod()),
                       
                        new Label("Quantite commandée:            "),
                        new Label("" + cp.getQuantite()),
                          new Label("Prix Total                                   "),
                        new Label(""+cp.getPrixtotal()),
                        
                      new Label("Etat du commande                                  "),
                        new Label(""+cp.getEtat()),
                        new Label("Payement ?                                  "),
                        new Label(""+cp.getPay())
                        
                        
            )
                    
                        
                
            );
               
            Container cButtons = new Container(new GridLayout(1,3));
            Button supprimer = new Button("Annuler");
            Button payer = new Button("Payer");
            Button pdf = new Button("Pdf");
             
               cButtons.addComponent(supprimer);
               cButtons.addComponent(payer);
               cButtons.addComponent(pdf);
           
         
        
            
       
            supprimer.addActionListener(e -> {
                if (cp.getEtat().equals("Validé"))
                {
                    Dialog.show("Erreur","Commande déjà validée",new Command("OK"));
                }
                else {
                    serviceTask.supprimerproduit(cp.getId());
                    removeAll();
                     Dialog.show("Success","Commande Annulée",new Command("OK"));
                    serviceTask.consultercommande();
                }
                });
  
       
           payer.addActionListener(new ActionListener() {
                 
            @Override
            public void actionPerformed(ActionEvent evt) {


            
                    ConnectionRequest con = new ConnectionRequest();
                    con.setUrl("http://localhost/integration/test1.1/web/app_dev.php/user/paycom?"
                            +"id="+cp.getId()
                            //+"&quantite="
                        
                    );
                    System.out.println(con.getUrl());
                     
             con.addResponseListener(new ActionListener<NetworkEvent>() {
                        @Override
                        public void actionPerformed(NetworkEvent evt) {
                            
                        
                        ToastBar.showMessage("Commande Payée avec succées.",FontImage.MATERIAL_DONE);
                        serviceTask.mail(cp.getId());
                            try {        
                            Form current = null;

                                new PanierForm(current).show();
                                
                                
                                
                                //serviceTask.consultercommande();
                            } catch (ParseException ex) {
                                
                            }
                         

                        
                        }
                    });
                    
                    con.setFailSilently(true);
                    NetworkManager.getInstance().addToQueueAndWait(con);
                    
               }

            
                });
            
           pdf.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
           Display.getInstance().setProperty("WebLoadingHidden", "true");

  
  BrowserComponent  browser = new BrowserComponent();

 
        browser.setURL("http://localhost/integration/test1.1/web/app_dev.php/mobile/pdfmob?"+
                "id="+cp.getId());
           Display.getInstance().execute(browser.getURL());


                    }});
           
           
 
           
            cDetail.addComponent(BorderLayout.SOUTH, cButtons);
           
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
