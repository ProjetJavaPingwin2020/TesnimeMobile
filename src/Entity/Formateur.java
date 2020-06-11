/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.InputStream;
import java.util.Date;


/**
 *
 * @author med
 */
public class Formateur {
    private int id;
    private String nom;
    private String prenom;
   
    private String nomImage;
    

    public Formateur() {
    }

    public Formateur(int id, String nom, String prenom, String nomImage) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nomImage = nomImage;
    }

    public Formateur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public Formateur(String nom, String prenom, String nomImage) {
        this.nom = nom;
        this.prenom = prenom;
        this.nomImage = nomImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    @Override
    public String toString() {
        return  nom ;
    }

   
    
    
    
    
}
