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
public class Question {
     private int id;
    private String question,categorie,id_user,email;
    private Date date;

    public Question(int id, String question, String categorie, String id_user, String email, Date date) {
        this.id = id;
        this.question = question;
        this.categorie = categorie;
        this.id_user = id_user;
        this.email = email;
        this.date = date;
    }

    public Question(int id, String question, String categorie, Date date) {
        this.id = id;
        this.question = question;
        this.categorie = categorie;
        this.date = date;
    }
    
    

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
