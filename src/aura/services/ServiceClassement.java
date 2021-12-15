/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.services;

import aura.entities.challenge;
import aura.entities.classement;
import aura.utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author NOUR
 */
public class ServiceClassement {
     boolean resultOK;
    ConnectionRequest req;
    static ServiceClassement instance;
    ArrayList<classement> classements = new ArrayList<>();
        ArrayList<classement> Allclassements = new ArrayList<>();

    
    
    public  ServiceClassement(){   
     req = new ConnectionRequest();
    }
    
     public static ServiceClassement getInstance(){
        
        if (instance == null) {
            instance = new ServiceClassement();
        }
        System.out.println("instance:"+instance);
        
        return instance;
        
    }
     
     public ArrayList<classement> parseJSONAction(String textJson){
        
        JSONParser j = new JSONParser();
        
         System.out.println("text json  :"+textJson);
      
        try {
            
             Map<String, Object> ClassementListJson = j.parseJSON(new CharArrayReader(textJson.toCharArray()));
             ArrayList<Map<String,Object>> ClassementList = (ArrayList<Map<String,Object>>) ClassementListJson.get("root");
             if (ClassementList!=null){System.out.println(ClassementList);} else {System.out.println("nooooop ClassementList vide return null ");}
            
             for ( Map<String, Object> obj : ClassementList) {
                 if (obj!=null){System.out.println(obj);} else {System.out.println("nooooop boucle for ");}
                
                classement c = new classement();
                
                float idNiveau = Float.parseFloat(obj.get("idNiveau").toString());
                 c.setIdNiveau((int)idNiveau);
                 
                c.setIdClient(obj.get("idClient").toString());
                

                float position = Float.parseFloat(obj.get("position").toString());
                
                c.setPosition((int)position);
                
                System.out.println(" c="+c);

                
                classements.add(c);
                                System.out.println("heeeerrrererererer ");


            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         System.out.println("json  challenges " +classements);
        
        return classements;  
    }
     
      public ArrayList<classement> getClassements(){
         
         

         String url =Statics.BASE_URL+"/classementMobile";
         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.getResponseContentType();
         System.out.println("********************************");
          System.out.println("fonction get classement ");


         System.out.println("request body "+request.getRequestBody());
         System.out.println("URL:"+request.getUrl());

         
         


         request.addResponseListener((evt)-> {
            
             Allclassements = parseJSONAction(new String(request.getResponseData()));
             System.out.println("Response data :" + new String(request.getResponseData()));
              System.out.println("Classement :" + Allclassements);
           //  request.removeResponseListener((ActionListener<NetworkEvent>) this);
             resultOK = req.getResponseCode()==200;
     });
         System.out.println("*******************************");
         
        
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return Allclassements;
    }
    
    

    
}
