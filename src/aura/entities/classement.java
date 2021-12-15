/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.entities;

/**
 *
 * @author NOUR
 */
public class classement {
    private int id,idNiveau,position,nbPoints;
    private String idClient;

    public classement() {
    }

    public classement(int id, int idNiveau, int position, int nbPoints, String idClient) {
        this.id = id;
        this.idNiveau = idNiveau;
        this.position = position;
        this.nbPoints = nbPoints;
        this.idClient = idClient;
    }

   

    public int getId() {
        return id;
    }

    public int getIdNiveau() {
        return idNiveau;
    }

    public int getPosition() {
        return position;
    }

    public String getIdClient() {
        return idClient;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }
    

    public void setId(int id) {
        this.id = id;
    }

    public void setIdNiveau(int idNiveau) {
        this.idNiveau = idNiveau;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return  "idNiveau=" + idNiveau + "position=" + position + "idClient=" + idClient + '}';
    }
    
    
    
    
    
}
