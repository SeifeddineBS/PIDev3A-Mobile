/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import aura.entities.Activites;
import aura.entities.Therapie;
import aura.entities.Therapie;
import aura.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceEvents {

    public ArrayList<Therapie> Therapi;
    public ArrayList<Activites> activite;

    
    public static ServiceEvents instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceEvents() {
         req = new ConnectionRequest();
    }

    public static ServiceEvents getInstance() {
        if (instance == null) {
            instance = new ServiceEvents();
        }
        return instance;
    }


    public ArrayList<Therapie> parseTasks(String jsonText){
        try {
            Therapi=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

          
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
         
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Therapie t = new Therapie();
                float id = Float.parseFloat(obj.get("id").toString());
               // int nombremax=Integer.parseInt(obj.get("nombremax").toString());
                t.setId((int)id);
                               t.setLieu(obj.get("lieu").toString());
                               t.setDate(obj.get("date").toString());
                               t.setSujet(obj.get("sujet").toString());
                             //  t.setIdcoach(obj.get("idcoach").toString());
                            //   t.setNombremax((int)nombremax);
                               

                //Ajouter la tâche extraite de la réponse Json à la liste
                Therapi.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        
        return Therapi;
    }
    
    
    
    
        public ArrayList<Activites> parseactivite(String jsonText){
        try {
            activite=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

          
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
         
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Activites t = new Activites();
                float id = Float.parseFloat(obj.get("id").toString());
               // int nombremax=Integer.parseInt(obj.get("nombremax").toString());
                t.setId((int)id);
                               t.setLieu(obj.get("lieu").toString());
                               t.setDate(obj.get("date").toString());
                               t.setDescription(obj.get("description").toString());
                             //  t.setIdcoach(obj.get("idcoach").toString());
                            //   t.setNombremax((int)nombremax);
                               

                //Ajouter la tâche extraite de la réponse Json à la liste
                activite.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        
        return activite;
    }

    
    public void ajoutEvent(Therapie ta) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = Statics.BASE_URL+"/api/ajouterTherapieapi?id=" + ta.getId()
                + "&date=" + ta.getDate()
                + "&sujet=" + ta.getSujet()
                + "&lieu=" + ta.getLieu()
                + "&idcoach=" + ta.getIdcoach()
                + "&nombremax=" + ta.getNombremax();
        con.setUrl(Url);
       con.setPost(false);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        Dialog.show("Succés", "Evenement ajouté", "ok", null);
    }
    public ArrayList<Therapie> getAllTasks(){
        String url = Statics.BASE_URL+"/api/listTherapieapi";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Therapi = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Therapi;
    }
      public ArrayList<Therapie> gettherapie(int id){
        String url = Statics.BASE_URL+"/api/therapieidapi/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Therapi = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Therapi;
    }
      
      public void rejoindre(int ta,String u) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = Statics.BASE_URL+"/api/rejoindreactapi/"+ta+"/"+u;
        con.setUrl(Url);
       con.setPost(false);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        Dialog.show("Succés", "Bienvenue", "ok", null);
    }
      
            public void rejoindreact(int ta,String u) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = Statics.BASE_URL+"/api/rejoindreacttapi/"+ta+"/"+u;
        con.setUrl(Url);
       con.setPost(false);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        Dialog.show("Succés", "Bienvenue", "ok", null);
    }

      
      
      
          public ArrayList<Activites> getAllact(){
        String url = Statics.BASE_URL+"/api/listActiviteapi";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                activite = parseactivite(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return activite;
    }

            public ArrayList<Activites> getactivite(int id){
        String url = Statics.BASE_URL+"/api/actidapi/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                activite = parseactivite(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return activite;
    }

      
            public void noteth(int ta,int note,String u) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = Statics.BASE_URL+"/api/AjouterNotethapi/"+ta+"/"+note+"/"+u;
        con.setUrl(Url);
       con.setPost(false);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        Dialog.show("Succés", "merci pour votre avis", "ok", null);
    }
            
             public void noteact(int ta,int note,String u) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = Statics.BASE_URL+"/api/AjouterNoteapi/"+ta+"/"+note+"/"+u;
        con.setUrl(Url);
       con.setPost(false);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        Dialog.show("Succés", "merci pour votre avis", "ok", null);
    }

      
                  public void liketh(int ta,int note,String u) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = Statics.BASE_URL+"/api/aimerthapi/"+ta+"/"+note+"/"+u;
        con.setUrl(Url);
       con.setPost(false);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        Dialog.show("Succés", "merci pour votre avis", "ok", null);
    }

                        public void likeact(int ta,int note,String u) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = Statics.BASE_URL+"/api/aimerActapi/"+ta+"/"+note+"/"+u;
        con.setUrl(Url);
       con.setPost(false);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        Dialog.show("Succés", "merci pour votre avis", "ok", null);
    }

      
      
}
