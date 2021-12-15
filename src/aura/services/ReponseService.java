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
import aura.entities.Reponse;
import aura.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nour Dekhil
 */
public class ReponseService {
    
    
       public static ReponseService instance = null;
     public boolean resultOK;
    private ConnectionRequest req;
    private ReponseService() {
        req = new ConnectionRequest();
    }
public static ReponseService getInstance(){
    if(instance == null)
        instance = new ReponseService();
    return instance;
}
    
    public ArrayList<Reponse> showReply(int idq){
    ArrayList<Reponse> result = new ArrayList<>();
    String url = Statics.BASE_URL + "/Reply/"+idq;
   
   
   req.setUrl(url);
    
    
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            JSONParser json;
            json = new JSONParser();
             try{
                 Map<String,Object> mapRep = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                 List<Map<String,Object>> lists= (List<Map<String,Object>>) mapRep.get("root");
                   for(Map<String,Object> obj : lists){
                     Reponse rep = new Reponse();
                     
                     float id = Float.parseFloat(obj.get("id").toString());
                     String reponse = obj.get("reponse").toString();
                    
                     
                     rep.setId((int)id);
                     rep.setReponse(reponse);
                     
                     result.add(rep);
                 }
             }catch(Exception e){
                 e.printStackTrace();
             }
             
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(req);
    return result;
}
    
    
    public boolean SupprimerObjectif(int id) {
        String url = Statics.BASE_URL + "/deleteReplyJSON/" +id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("rr"+req.getResponseData());
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
     public boolean addReply(int idq, String rep,String username) {
        String url = Statics.BASE_URL + "/addReplyJSON/new/" + idq + "/" +username+ "?reponse=" + rep ;
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
     
     public boolean UpdateReply(int id, String newre) {
        String url = Statics.BASE_URL + "/updateReplyJSON/" + id + "?reponse=" +newre;
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


