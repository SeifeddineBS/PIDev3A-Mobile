/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aura.entities;

/**
 *
 * @author akram
 */
public class article {
      private int id;
    private String titre;
    private String theme;
    private String nom_auteur;
    private String date;
    private String article;
    private String id_user;
    private int approuver;
public article() {
        
    }
    public article(int id, String theme, String nom_auteur, String date, String article, String id_user, int approuver) {
        this.id = id;
        this.theme = theme;
        this.nom_auteur = nom_auteur;
        this.date = date;
        this.article = article;
        this.id_user = id_user;
        this.approuver = approuver;
    }
    public article( String theme, String nom_auteur, String date, String article, String id_user) {
        
        this.theme = theme;
        this.nom_auteur = nom_auteur;
        this.date = date;
        this.article = article;
        this.id_user = id_user;
        
    }

    public article(String text, String text0, String text1, String text2, String text3, String text4) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getNom_auteur() {
        return nom_auteur;
    }

    public void setNom_auteur(String nom_auteur) {
        this.nom_auteur = nom_auteur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public int getApprouver() {
        return approuver;
    }

    public void setApprouver(int approuver) {
        this.approuver = approuver;
    }

    @Override
    public String toString() {
        return "article{" + "id=" + id + ", titre=" + titre + ", theme=" + theme + ", nom_auteur=" + nom_auteur + ", date=" + date + ", article=" + article + ", id_user=" + id_user + ", approuver=" + approuver + '}';
    }
    
}

