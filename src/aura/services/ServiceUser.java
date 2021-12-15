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
import com.codename1.ui.events.ActionListener;
import aura.entities.User;
import aura.utils.Statics;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import static com.codename1.util.Base64.encodeNoNewline;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceUser {

    public ArrayList<User> tasks;

    public String loginResult;

    public static ServiceUser instance = null;
    public Boolean resultOK;
    public String resultAdd;
    private ConnectionRequest req;

    private ServiceUser() {
        req = new ConnectionRequest();
    }

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }

    public String checkUserUnique(User u) {
        String url = Statics.BASE_URL + "/api/client/checkUserUnique?id=" + u.getId() + "&email=" + u.getEmail();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultAdd = new String(req.getResponseData());
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultAdd;
    }

    public String addUser(User u) {
        String url = Statics.BASE_URL + "/api/client/addUser?id=" + u.getId() + "&nom=" + u.getNom() + "&prenom=" + u.getPrenom() + "&email=" + u.getEmail() + "&password=" + u.getPassword() + "&tel=" + u.getTel() + "&picture=" + u.getPicture();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultAdd = new String(req.getResponseData());
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultAdd;
    }

    public boolean modifierUser(User u) {
        String url = Statics.BASE_URL + "/api/client/modify?id=" + u.getId() + "&nom=" + u.getNom() + "&prenom=" + u.getPrenom() + "&tel=" + u.getTel()+"&picture="+u.getPicture();
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

    public boolean modifierUserMotDePasse(User u) {

        String url = Statics.BASE_URL + "/api/client/modifyPassword?id=" + u.getId() + "&password=" + u.getPassword();
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
    
  


    public String loginCheck(String email, String password) {
        String url = Statics.BASE_URL + "/api/client/loginCheck?email=" + email + "&password=" + password;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                loginResult = new String(req.getResponseData());
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return loginResult;
    }

    public ArrayList<User> parseUser(String jsonText) {
        try {
            tasks = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                User user = new User();
                float id = Float.parseFloat(obj.get("id").toString());
                user.setId(obj.get("id").toString());
                user.setNom(obj.get("nom").toString());
                user.setPrenom(obj.get("prenom").toString());
                user.setEmail(obj.get("email").toString());
                user.setPassword(obj.get("password").toString());
                user.setTel(obj.get("tel").toString());
                user.setAdresse(obj.get("adresse").toString());
                user.setPicture(obj.get("picture").toString());
                user.setSms(obj.get("sms").toString());

                //Ajouter la tâche extraite de la réponse Json à la liste
                tasks.add(user);
            }

        } catch (IOException ex) {

        }

        return tasks;
    }

    public User getUser(String username) {

        String url = Statics.BASE_URL + "/api/client/getUser?email=" + username;

        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseUser(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks.get(0);
    }

    public String forgetPasswordCheck(String cin, String email) {
        String url = Statics.BASE_URL + "/api/client/forgetPasswordCheck?cin=" + cin + "&email=" + email ;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                loginResult = new String(req.getResponseData());
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return loginResult;
    }

    public boolean smsAuthentification(User u) {
        String url = Statics.BASE_URL + "/api/client/smsAuthentification?id=" + u.getId();
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

    public boolean sendSms(String username, String verificationCode) {
        String url = Statics.BASE_URL + "/api/client/sendSms?id=" + username + "&verificationCode=" + verificationCode;
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

    public boolean testData() throws IOException {
        String file="file://C:/Users/SeifBS/AppData/Local/Temp/temp3230432898948394292s..jpg";
        String data = encodeNoNewline(Util.readInputStream(FileSystemStorage.getInstance().openInputStream(file)));
        String url = Statics.BASE_URL + "/api/client/testData?data=" + data;
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
