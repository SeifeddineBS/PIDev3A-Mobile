/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.challenge_classement;

import aura.entities.User;
import aura.entities.classement;
import aura.gui.objectif.ObjectifForm;
import aura.services.ServiceClassement;
import aura.services.ServiceUser;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;
import java.util.Iterator;

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
public class Classement extends Form {

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
            new Listth(current, this.username);

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
    public Classement(String username) {

        ArrayList<classement> classements = new ArrayList<>();
        classement classement = new classement();

        classements = ServiceClassement.getInstance().getClassements();

        this.username = username;
        this.user = ServiceUser.getInstance().getUser(this.username);
         sideBar(username);
        String IdUser = this.user.getId();
        this.setTitle("Classement");
        //this.setLayout(BoxLayout.y());
        // this.setLayout(new TableLayout(2, 2));

        //*************** spanlabel ******************
        /* SpanLabel tasksListSP = new SpanLabel();
         for (Iterator<classement> it = classements.iterator(); it.hasNext();) {
            classement = it.next();
            
            
             tasksListSP.setText(classement.toString());
             System.out.println("liste="+tasksListSP.getText());
         
         }
        
        
        tasksListSP.setText(ServiceClassement.getInstance().getClassements().toString());
        
        
        
        this.add(tasksListSP);
         this.show();*/
        this.setLayout(new BorderLayout());
        Container listeCL = new Container(BoxLayout.y());
        listeCL.setScrollableY(true);
        for (Iterator<classement> it = classements.iterator(); it.hasNext();) {
            classement = it.next();

            MultiButton mbCL = new MultiButton();

            mbCL.setTextLine1("Position: " + Integer.toString(classement.getPosition()));
            mbCL.setTextLine2(" Client: " + classement.getIdClient());
            mbCL.setTextLine3("Niveau: " + Integer.toString(classement.getIdNiveau()));

            mbCL.setTextLine4("Nb Points: " + Integer.toString(classement.getNbPoints()));

            listeCL.add(mbCL);
            //  mb.isSelected(); chekbox selected 

        }

        this.add(CENTER, listeCL);
        this.show();

        Form formm = new ObjectifForm(username);

    }

}
