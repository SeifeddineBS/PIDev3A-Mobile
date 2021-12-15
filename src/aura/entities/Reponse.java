/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.entities;

import java.util.Date;

/**
 *
 * @author Nour Dekhil
 */
public class Reponse {
    private int id;
    private String reponse;
    private Question idQuestion;
    private Date createdAt;

    public Reponse(int id, String reponse, Question idQuestion, Date createdAt) {
        this.id = id;
        this.reponse = reponse;
        this.idQuestion = idQuestion;
        this.createdAt = createdAt;
    }

    public Reponse(int id, String reponse) {
        this.id = id;
        this.reponse = reponse;
    }

    
    public Reponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Question getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Question idQuestion) {
        this.idQuestion = idQuestion;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
}
