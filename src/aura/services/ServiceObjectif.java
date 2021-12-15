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
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import aura.entities.User;
import aura.entities.Objectif;
import aura.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chirine
 */
public class ServiceObjectif {

    public ArrayList<Objectif> objpred;

    public static ServiceObjectif instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceObjectif() {
        req = new ConnectionRequest();
    }

    public static ServiceObjectif getInstance() {
        if (instance == null) {
            instance = new ServiceObjectif();
        }
        return instance;
    }

    public ArrayList<Objectif> parseObjectifs(String jsonText) {
        try {
            objpred = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> objectifsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) objectifsListJson.get("root");

            for (Map<String, Object> obj : list) {
                Objectif t = new Objectif();
                //!!!!!!!!!!!!!!!
                User c = new User();
                c.setId("12345698");
                //!!!!!!!!!!!!!!!!!!!!
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);

                float rep = Float.parseFloat(obj.get("reponse").toString());
                t.setReponse((int) rep);

                t.setDescription(obj.get("description").toString());
                t.setDate(obj.get("datedebut").toString());
                
                float duree = Float.parseFloat(obj.get("duree").toString());
                t.setDuree((int) duree);
                t.setIcone(obj.get("image").toString());
                
                objpred.add(t);
            }
        } catch (IOException ex) {

        }
        return objpred;
    }

    public ArrayList<Objectif> getAllObjectifs() {
        String url = Statics.BASE_URL + "/api/mesObjectifs";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                objpred = parseObjectifs(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return objpred;
    }

    public boolean addObjectif(Objectif t) {
        String url = Statics.BASE_URL + "/api/ajouterObjectif?description=" + t.getDescription() + "&reponse=" + t.getReponse() + "&dateDebut=" + t.getDate() + "&duree=" + t.getDuree();
        req.setUrl(url);
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

    public boolean modifierObjectif(Objectif t) {
        String url = Statics.BASE_URL + "/api/modifierObjectif/" + t.getId() + "?description=" + t.getDescription() + "&reponse=" + t.getReponse() + "&dateDebut=" + t.getDate() + "&duree=" + t.getDuree();
        req.setUrl(url);
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
    
    public boolean SupprimerObjectif(Objectif t) {
        String url = Statics.BASE_URL + "/api/supprimerObjectif/" + t.getId();
        req.setUrl(url);
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

}
