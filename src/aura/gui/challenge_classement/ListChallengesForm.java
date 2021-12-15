/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.challenge_classement;

import aura.entities.User;
import aura.entities.challenge;
import static aura.gui.activiteTherapie.Listact.user;
import aura.gui.objectif.ObjectifForm;
import static aura.gui.objectif.ObjectifForm.username;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import aura.services.ServiceChallenge;
import aura.services.ServiceUser;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import java.util.ArrayList;
//Imports GUI
import aura.gui.gestionUser.HomeClient;
import aura.gui.HomeForm;
import aura.gui.challenge_classement.Classement;
import aura.gui.challenge_classement.ListChallengesForm;
import aura.gui.activiteTherapie.Listact;
import aura.gui.activiteTherapie.Listact;
import aura.gui.activiteTherapie.Listth;
import aura.gui.activiteTherapie.Listth;
import aura.gui.challenge_classement.MesChallenges;
import aura.gui.article.HomeArticle;
import aura.gui.entraide.AllQuestion;
import aura.gui.objectif.ObjectifForm;
// End 
// SideBar Imports
import com.codename1.ui.Toolbar;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import java.io.IOException;

//End 
/**
 *
 * @author NOUR
 */
public class ListChallengesForm extends Form{
       Form current;
    User user;
    String username;
    
        public void sideBar(String username)
    {
   Toolbar tb = this.getToolbar();
        //Image icon=theme.getImage("logo.png");
        Container topbar = BorderLayout.east(new Label());
        topbar.add(BorderLayout.SOUTH, new Label("AURA"));
        topbar.setUIID("SideCommand");
        tb.addComponentToSideMenu(topbar);

        tb.addMaterialCommandToSideMenu("Therapie", FontImage.MATERIAL_RECORD_VOICE_OVER, ev -> {
            new Listth(current, username);

        });
        tb.addMaterialCommandToSideMenu("Activite", FontImage.MATERIAL_EXTENSION, ev -> {
            new Listact(current, username);

        });
        tb.addMaterialCommandToSideMenu("Objectifs", FontImage.MATERIAL_CHECK, ev -> {
            new ObjectifForm(username).show();

        });
        tb.addMaterialCommandToSideMenu("Articles", FontImage.MATERIAL_ARTICLE, ev -> {
            new HomeArticle(username).show();

        });
        tb.addMaterialCommandToSideMenu("Liste des Challenges", FontImage.MATERIAL_ALL_INBOX, ev -> {
            new ListChallengesForm(username).show();
        });
        tb.addMaterialCommandToSideMenu("Mes Challenges", FontImage.MATERIAL_TOUR, ev -> {
            new MesChallenges(username).show();
        });
        tb.addMaterialCommandToSideMenu("Classement", FontImage.MATERIAL_LEADERBOARD, ev -> {
            new Classement(username).show();
        });
        tb.addMaterialCommandToSideMenu("Entraide", FontImage.MATERIAL_CONTACT_PAGE, ev -> {
            new AllQuestion(username).show();
        });
        tb.addMaterialCommandToSideMenu("Mon Profil", FontImage.MATERIAL_ACCOUNT_CIRCLE, ev -> {
            try {
                new HomeClient(username).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });  
         tb.addMaterialCommandToSideMenu("Se deconnecter", FontImage.MATERIAL_LOGOUT, ev -> {

            try {
                new HomeForm().show();
                // homeClient(current).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    
    
    }
    public ListChallengesForm(String username){
        
          ArrayList<challenge> challenges = new ArrayList<>();
          challenge challenge= new challenge();

               
           challenges=ServiceChallenge.getInstance().getChallenges();
           boolean test;
            this.username=username;
       this.user=ServiceUser.getInstance().getUser(this.username);
       String IdUser=this.user.getId();
        System.out.println(this.user.getId());
        sideBar(username);
           
        this.setTitle("List des Challenges ");
        //this.setLayout(BoxLayout.y());
        // this.setLayout(new TableLayout(2, 2));
       

        //*************** spanlabel ******************
        
       /* SpanLabel tasksListSP = new SpanLabel();
        tasksListSP.setText(ServiceChallenge.getInstance().getChallenges().toString());
        System.out.println("liste="+tasksListSP.getText());
       if(tasksListSP.getText()!="") {System.out.println("niiiiiiiiice");} else {System.out.println("faiiiiiiiiiiil ");}
        
        
        this.add(tasksListSP);*/
       
     
      
        
        this.setLayout(new BorderLayout());
        Container listeCH =new Container (BoxLayout.y());
        listeCH.setScrollableY(true);
         for (challenge ch : challenges) {
          
            String tr=ch.getTitre();
            
            MultiButton mbCH= new MultiButton("titre : "+tr+"   de niv:"+Integer.toString(ch.getIdNiveau()));
            mbCH.setTextLine2(ch.getDateDebut().toString());
             mbCH.setTextLine3(ch.getDateFin().toString());
              mbCH.setTextLine4(ch.getDescription());
           mbCH.setCheckBox(false);
            Button btnRE=new Button("rejoindre");
             System.out.println("challenge id :"+ch.getId());
             System.out.println(" challenge ch"+ch);
             
           //  btnRE.addActionListener(y-> ServiceChallenge.getInstance().rejoindreChallenges(ch.getId(),IdUser));
            btnRE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
              // ServiceChallenge.getInstance().rejoindreChallenges(ch.getId(),IdUser);
                
                
                  if( ServiceChallenge.getInstance().rejoindreChallenges(ch.getId(),IdUser)){Dialog.show("error", "vous n'avez pas participer au challenge", new Command("OK"));}
             else {Dialog.show("succes", "vous avez  participer au challenge", new Command("OK"));}
                
                
            }});
             /*if( ServiceChallenge.getInstance().rejoindreChallenges(ch.getId(),IdUser)){Dialog.show("Success", "vous avez participer au challenge", new Command("OK"));}
             else {Dialog.show("error", "vous n'avez pas participer au challenge", new Command("OK"));}*/
             listeCH.add(mbCH);
            listeCH.add(btnRE);
            
           // if(mb.isSelected()){ test=ServiceChallenge.getInstance().rejoindreChallenges(challenge.getId(),"87654321");}
          //  mb.isSelected(); chekbox selected 
          
           }
         
         this.add(CENTER,listeCH);
         this.show();
         
     
     
     
   
     
      Form formm = new ObjectifForm(username);
        
        
    }
    
}
