/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aura.services;

import aura.entities.article;
import aura.services.ServiceArticle;
import aura.utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author akram
 */
public class ServiceArticle {
    
    //var
    boolean resultOK;
    ConnectionRequest req;
    static ServiceArticle instance=null;
    public ArrayList<article> articles = new ArrayList<>() ;
     String text ;
    
    
    //constructor
    public ServiceArticle() {
        req = new ConnectionRequest();
    }
    
    //SINGLETON
    public static ServiceArticle getInstance(){
        
        if (instance == null) {
            instance = new ServiceArticle();
        }
        
        return instance;
    }
    
    
    
    
    //ADD TASK 
    public boolean addTaskAction(article t){
        
String url = Statics.BASE_URL + "/add?titre=" + t.getTitre()+ "&theme=" + t.getTheme()+ "&nomauteur=" + t.getNom_auteur()+ "&date=" + t.getDate()+ "&article=" + t.getArticle()+ "&iduser=" + t.getId_user()+ "&approuver=" + t.getApprouver();        
 // String url = Statics.BASE_URL+"/add?titre=titre&theme=theme&nomauteur=nomauteur&date=date&article=article&iduser=iduser&approuver=0";
        System.out.println(url);
        req.setUrl(url);
         req.setPost(false);
        req.addResponseListener((evt) -> {
            resultOK = req.getResponseCode()==200;
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    
    
    //PARSE TASKS JSON : convert JSON to java objects
    public ArrayList<article> parseactivite(String jsonText){
        try {
            articles=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

          
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.println("ici");
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                System.out.println("ici2");
                //Création des tâches et récupération de leurs données
                article t = new article();
                float id = Float.parseFloat(obj.get("id").toString());
               // int nombremax=Integer.parseInt(obj.get("nombremax").toString());
                t.setId((int)id);
                               t.setTitre(obj.get("titre").toString());
                               t.setTheme(obj.get("theme").toString());
                               t.setNom_auteur(obj.get("nomAuteur").toString());
                               t.setDate(obj.get("date").toString());
                               t.setArticle(obj.get("article").toString());
                               t.setId_user(obj.get("idUser").toString());
                             //  t.setIdcoach(obj.get("idcoach").toString());
                            //   t.setNombremax((int)nombremax);
                               System.out.println("fin praser");

                //Ajouter la tâche extraite de la réponse Json à la liste
                articles.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        
        return articles;
    }

 public ArrayList<article> getAllObjectifs() {
        String url = Statics.BASE_URL +"/liste";
         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 
             articles = parseactivite(new String(request.getResponseData()));
             request.removeResponseListener((ActionListener<NetworkEvent>) this);
             }
         });
         
        NetworkManager.getInstance().addToQueueAndWait(request);
        return articles;
    }
    public boolean updateEvent(article t, int id) {
       String url = Statics.BASE_URL + "/update/"+id+"?titre=" + t.getTitre()+ "&theme=" + t.getTheme()+ "&nomauteur=" + t.getNom_auteur()+ "&date=" + t.getDate()+ "&article=" + t.getArticle()+ "&iduser=" + t.getId_user()+ "&approuver=" + t.getApprouver();        

        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

     public boolean deletedArticle(int id) {
       
        String url = Statics.BASE_URL + "/deletearticle/"+id+"";  
        req.setUrl(url);
        req.setPost(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);               
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
 
     
     public boolean addarticle(article e, String file) {
        String url = Statics.BASE_URL + "/adda";
        String json = ("{\"file\":\"" + file + "\",\"titre\":\"" + e.getTitre()+ "\",\"theme\":\"" + e.getTheme()+ "\",\"nomauteur\":\"" + e.getNom_auteur() + "\",\"date\":\"" + e.getDate() + "\",\"iduser\":\"" + e.getId_user()+"\",\"approuver\":" + e.getApprouver()+"}");
        req.addRequestHeader("accept", "application/json");
        req.setRequestBody(json);
        req.setUrl(url);
        req.setPost(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
                
                System.out.println(req.getRequestBody());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
     public  String parsepdf(String jsonText) {
         
        try {
           
            
            JSONParser j = new JSONParser();
            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            System.out.println(usersListJson);
            
                text = usersListJson.get("text").toString();
                
            System.out.println(text);

        } catch (IOException ex) {

        }
        return text ;
        
    }
     
     public String gettext (int id)
     {
     String url = Statics.BASE_URL+"/pdftotext/"+id+"";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                text = parsepdf(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
       
        
        return text;}
  public boolean favo(int id,String idu) {
       
        String url = Statics.BASE_URL + "/favo/"+id+"/"+idu+"";  
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);               
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
  public ArrayList<article> getAllfavo(String id ) {
        String url = Statics.BASE_URL +"/listefavo/"+id+"";
         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 
             articles = parseactivite(new String(request.getResponseData()));
             request.removeResponseListener((ActionListener<NetworkEvent>) this);
             }
         });
         
        NetworkManager.getInstance().addToQueueAndWait(request);
        return articles;
    }
     
}