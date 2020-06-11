/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author milim
 */
public class Evenement {

    private int idEvenement;
    private String nomEvenement;
    private String dateEvenement;
    private String adresse;
    private String description;
    private int chefId;
    private String nomImage;

    public Evenement(int idEvenement, String nomEvenement, String dateEvenement, String adresse, String description, int chefId, String nomImage) {
        this.idEvenement = idEvenement;
        this.nomEvenement = nomEvenement;
        this.dateEvenement = dateEvenement;
        this.adresse = adresse;
        this.description = description;
        this.chefId = chefId;
        this.nomImage = nomImage;
    }

    public Evenement() {
    }

    public Evenement(int idEvenement, String nomEvenement, String adresse, String description, int chefId, String nomImage) {
        this.idEvenement = idEvenement;
        this.nomEvenement = nomEvenement;
        this.adresse = adresse;
        this.description = description;
        this.chefId = chefId;
        this.nomImage = nomImage;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    public String getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(String dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChefId() {
        return chefId;
    }

    public void setChefId(int chefId) {
        this.chefId = chefId;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

}
