/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.entities;

import java.util.*;

/**
 *
 * @author NOUR
 */
public class challenge {
    
    private int id,nbPartcipants,idNiveau;
    private String titre,type,img;
    private  Date dateDebut,dateFin;
    private String description;
    

    public challenge(int id, int nbPartcipants, int idNiveau, String titre, String type, String img, Date dateDebut, Date dateFin, String description) {
        this.id = id;
        this.nbPartcipants = nbPartcipants;
        this.idNiveau = idNiveau;
        this.titre = titre;
        this.type = type;
        this.img = img;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
    }

  

    public challenge() {
    }

    public int getId() {
        return id;
    }

    public int getNbPartcipants() {
        return nbPartcipants;
    }

    public String getTitre() {
        return titre;
    }

    public String getType() {
        return type;
    }

    public String getImg() {
        return img;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getDescription() {
        return description;
    }

    public int getIdNiveau() {
        return idNiveau;
    }
    

    public void setId(int id) {
        this.id = id;
    }

    public void setNbPartcipants(int nbPartcipants) {
        this.nbPartcipants = nbPartcipants;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdNiveau(int idNiveau) {
        this.idNiveau = idNiveau;
    }
    String nL1 = System.getProperty("line.separator"); 
   // String nL2=System.lineSeparator();

    @Override
    public String toString() {
        return "challenge{" + "id=" + id + ", nbPartcipants=" + nbPartcipants + ", idNiveau=" + idNiveau + ", titre=" + titre + ", type=" + type + ", img=" + img + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", description=" + description + ", nL1=" + nL1 + '}';
    }
    
    
    
    
}
