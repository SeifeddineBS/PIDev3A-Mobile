/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.entities;

/**
 *
 * @author bhk
 */
public class User {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String tel;
    private String adresse;
    private String specialite;
    private String picture;
    private String role;
    private String sms;   

    public User(String id, String nom, String prenom, String email, String password, String tel,String picture) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.picture=picture;
    }
    
     public User(String id,String nom, String prenom,String tel,String picture) {
         this.id=id;
        this.nom = nom;
        this.prenom = prenom;        
        this.tel = tel;
        this.picture=picture;
    }
     
      public User(String id,String password) {
         this.id=id;
        this.password =password;
        
    }
    
    public User()
    {}

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTel() {
        return tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getSpecialite() {
        return specialite;
    }

    public String getPicture() {
        return picture;
    }

    public String getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
     public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", password=" + password + ", tel=" + tel + ", adresse=" + adresse + ", specialite=" + specialite + ", picture=" + picture + ", role=" + role + '}';
    }
    
    
    
    
    
    
    
    
}
