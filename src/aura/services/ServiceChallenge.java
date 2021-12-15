/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import aura.entities.challenge;
import aura.utils.Statics;
import java.text.ParseException;
import java.text.SimpleDateFormat;



/**
 *
 * @author NOUR
 */
public class ServiceChallenge {
     boolean resultOK;
    ConnectionRequest req;
    static ServiceChallenge instance;
    ArrayList<challenge> challenges = new ArrayList<>();
    ArrayList<challenge> AllChallenges = new ArrayList<>();
        ArrayList<challenge>MyChallenges = new ArrayList<>();


    

    public ServiceChallenge() {
       req = new ConnectionRequest();
    }
    
     
    public static ServiceChallenge getInstance(){
        
        if (instance == null) {
            instance = new ServiceChallenge();
        }
        System.out.println("instance:"+instance);
        
        return instance;
        
    }
    
    
    
     public ArrayList<challenge> parseJSONAction(String textJson){
        
        JSONParser j = new JSONParser();
        
         System.out.println("text json  :"+textJson);
      
        try {
             Map<String, Object> ChallengesListJson = j.parseJSON(new CharArrayReader(textJson.toCharArray()));
             ArrayList<Map<String,Object>> ChallengesList = (ArrayList<Map<String,Object>>) ChallengesListJson.get("root");
             if (ChallengesList!=null){System.out.println(ChallengesList);} else {System.out.println("nooooop ChallengeList vide return null ");}
            
             for ( Map<String, Object> obj : ChallengesList) {
                 if (obj!=null){System.out.println(obj);} else {System.out.println("nooooop boucle for ");}
                
                challenge c = new challenge();
                /*float id = Float.parseFloat(obj.get("id").toString());
                c.setId((int)id);*/
                float nbParticipants = Float.parseFloat(obj.get("nbParticipants").toString());
                 float id = Float.parseFloat(obj.get("id").toString());
                c.setId((int)id);

                c.setNbPartcipants((int)nbParticipants);
                c.setTitre(obj.get("titre").toString());
                c.setType(obj.get("type").toString());
                c.setDescription(obj.get("description").toString());
//                 c.setImg(obj.get("img").toString());

                float idNiveau = Float.parseFloat(obj.get("idNiveau").toString());
                
                c.setIdNiveau((int)idNiveau);
                 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

               

                 try {
                     c.setDateDebut(format.parse(obj.get("dateDebut").toString()));
                 } catch (ParseException ex) {
                              System.out.println("erreur date debut ");

                 }
                 try {
                     c.setDateFin(format.parse(obj.get("dateFin").toString()));
                 } catch (ParseException ex) {
                      System.out.println("erreur date fin ");
                 }
                System.out.println(" c="+c);

                
                challenges.add(c);

            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         System.out.println("json  challenges " +challenges);
        
        return challenges;  
    }
     
     
     public ArrayList<challenge> parseJSONAction2(String textJson){
        
        JSONParser j = new JSONParser();
        
         System.out.println("text json  :"+textJson);
      
        try {
             Map<String, Object> ChallengesListJson = j.parseJSON(new CharArrayReader(textJson.toCharArray()));
             System.out.println(ChallengesListJson);
             ArrayList<Map<String,Object>> ChallengesList = (ArrayList<Map<String,Object>>) ChallengesListJson.get("root");
             if (ChallengesList!=null){System.out.println(ChallengesList);} else {System.out.println("nooooop ChallengeList vide return null ");}
            
             for ( Map<String, Object> obj : ChallengesList) {
                 if (obj!=null){System.out.println(obj);} else {System.out.println("nooooop boucle for ");}
                
                challenge c = new challenge();
                /*float id = Float.parseFloat(obj.get("id").toString());
                c.setId((int)id);*/
                float nbParticipants = Float.parseFloat(obj.get("nbParticipants").toString());
                 float id = Float.parseFloat(obj.get("id").toString());
                c.setId((int)id);

                c.setNbPartcipants((int)nbParticipants);
                c.setTitre(obj.get("titre").toString());
                c.setType(obj.get("type").toString());
                c.setDescription(obj.get("description").toString());
             //    c.setImg(obj.get("img").toString());

                float idNiveau = Float.parseFloat(obj.get("idNiveau").toString());
                
                c.setIdNiveau((int)idNiveau);
                 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

               

                 try {
                     c.setDateDebut(format.parse(obj.get("dateDebut").toString()));
                 } catch (ParseException ex) {
                              System.out.println("erreur date debut ");

                 }
                 try {
                     c.setDateFin(format.parse(obj.get("dateFin").toString()));
                 } catch (ParseException ex) {
                      System.out.println("erreur date fin ");
                 }
                System.out.println(" c="+c);

                
                challenges.add(c);

            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         System.out.println("json  challenges " +challenges);
        
        return challenges;  
    }
    
    
     public ArrayList<challenge> getChallenges(){
         
         

         String url =Statics.BASE_URL+"/challengesMobile";
         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.getResponseContentType();
         System.out.println("********************************");
          System.out.println("fonction get challenge ");


         System.out.println("request body "+request.getRequestBody());
         System.out.println("URL:"+request.getUrl());

         
         


         request.addResponseListener((evt)-> {
            
             AllChallenges = parseJSONAction(new String(request.getResponseData()));
             System.out.println("Response data :" + new String(request.getResponseData()));
              System.out.println("AllChallenges :" + AllChallenges);
           //  request.removeResponseListener((ActionListener<NetworkEvent>) this);
             resultOK = req.getResponseCode()==200;
     });
         System.out.println("*******************************");
         
        
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return AllChallenges;
    }
     
     
      public ArrayList<challenge> getMesChallenges(String userId){
         
         

         String url =Statics.BASE_URL+"/MyChallengesMobiles/"+userId;
          System.out.println(url);
         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.getResponseContentType();
         System.out.println("********************************");
          System.out.println("fonction get challenge ");


         System.out.println("request body "+request.getRequestBody());
         System.out.println("URL:"+request.getUrl());
         request.addResponseListener((evt)-> {
             System.out.println(new String(request.getResponseData()));
            
             MyChallenges = parseJSONAction2(new String(request.getResponseData()));
             System.out.println("Response data :" + new String(request.getResponseData()));
              System.out.println("MyChallenges :" + MyChallenges);
           //  request.removeResponseListener((ActionListener<NetworkEvent>) this);
             resultOK = req.getResponseCode()==200;
     });
         System.out.println("*******************************");
         
        
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return MyChallenges;
    }
      
      public boolean rejoindreChallenges(int id ,String client){
         
         

         String url =Statics.BASE_URL+"/JsonrejoindreChallenge/"+id+"/"+client;
         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.getResponseContentType();
         System.out.println("********************************");
          System.out.println("fonction rejoindre challenge ");


         System.out.println("request body "+request.getRequestBody());
         System.out.println("URL:"+request.getUrl());

         
         


         request.addResponseListener((evt2)-> {
            
             System.out.println("Response data :" + new String(request.getResponseData()));
           //  request.removeResponseListener((ActionListener<NetworkEvent>) this);
             resultOK = req.getResponseCode()==400;
     });
         System.out.println("*******************************");
         
        
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOK;
    }
    
    //finirChallenges
      
       public boolean finirChallenges(challenge c,String client){
         
         System.out.println("********   im in   ******** **");

         String url =Statics.BASE_URL+"/JsonfinirChallenge/"+c.getId()+"/"+client;
         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.getResponseContentType();
         System.out.println("********************************");
          System.out.println("fonction finir challenge ");


         System.out.println("request body "+request.getRequestBody());
         System.out.println("URL:"+request.getUrl());

         
         


         request.addResponseListener((evt)-> {
            
             System.out.println("Response data :" + new String(request.getResponseData()));
             resultOK = req.getResponseCode()==500;
     });
         System.out.println("*******************************");
         
        
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return resultOK;
    }
      
    
    
}
