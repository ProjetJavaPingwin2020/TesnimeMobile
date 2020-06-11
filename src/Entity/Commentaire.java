/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import com.codename1.ui.Form;
import java.util.Date;

/**
 *
 * @author USER
 */
public class Commentaire {
    private String Contenu;
    private Date DatePub;
    private int User;
    public int Article;
    private int id;
    private String contenuArticle;

    public String getContenuArticle() {
        return contenuArticle;
    }

    public void setContenuArticle(String contenuArticle) {
        this.contenuArticle = contenuArticle;
    }
    
    public int getUser() {
        return User;
    }

    public void setUser(int User) {
        this.User = User;
    }


    public Commentaire(Form current) {
 this.Contenu=Contenu;
   this.DatePub=DatePub;
    this.Article=Article;
   this.id=id;
    }
  public Commentaire(String Contenu,Integer Article) {
 this.Contenu=Contenu;
   this.DatePub=DatePub;
    this.Article=Article;
   //this.id=id;
    }
    public Commentaire() {

    }

    public String getContenu() {
        return Contenu;
    }

    public void setContenu(String Contenu) {
        this.Contenu = Contenu;
    }

    public Date getDatePub() {
        return DatePub;
    }

    public void setDatePub(Date DatePub) {
        this.DatePub = DatePub;
    }

  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticle() {
        return Article;
    }

    public void setArticle(int Article) {
        this.Article = Article;
    }

   

  

    
    
}
