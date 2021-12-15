/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.challenge_classement;

import aura.entities.User;
import static com.codename1.ui.Component.CENTER;
import java.util.*;
import aura.entities.challenge;
import aura.gui.objectif.ObjectifForm;
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
public class MesChallenges extends Form {

    Form current;
    User user;
    String username;

    public void sideBar(String username) {
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

    public MesChallenges(String username) {

        ArrayList<challenge> Meschallenges = new ArrayList<>();
        challenge challenge = new challenge();
        boolean test;
        this.username = username;
        this.user = ServiceUser.getInstance().getUser(this.username);

        String idUser = this.user.getId();
        Meschallenges = ServiceChallenge.getInstance().getMesChallenges(idUser);

        System.out.println(this.user.getId());
        sideBar(username);

        this.setTitle(" Mes Challenges ");

        this.setLayout(new BorderLayout());
        Container listeMC = new Container(BoxLayout.y());
        listeMC.setScrollableY(true);

        for (challenge ch : Meschallenges) {

            String tr = ch.getTitre();

            MultiButton mbMC = new MultiButton("titre : " + tr + "   de niv:" + Integer.toString(ch.getIdNiveau()));
            mbMC.setTextLine2(ch.getDateDebut().toString());
            mbMC.setTextLine3(ch.getDateFin().toString());
            mbMC.setTextLine4(ch.getDescription());
            // mb.isSmoothScrolling();

            mbMC.setCheckBox(false);
            Button btnFI = new Button("finir");
            btnFI.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent evt) {

                    // ServiceChallenge.getInstance().rejoindreChallenges(ch.getId(),IdUser);
                    if (ServiceChallenge.getInstance().finirChallenges(ch, idUser)) {
                        Dialog.show("error", "vous n'avez pas participer au challenge", new Command("OK"));
                    } else {
                        Dialog.show("succes", "Felicitations le challenge est fini ", new Command("OK"));
                    }

                }
            });
            // btnFI.addActionListener(z-> ServiceChallenge.getInstance().finirChallenges(ch,IdUser));
            listeMC.add(mbMC);
            listeMC.add(btnFI);

            // if(mb.isCheckBox()){ System.out.println("check box  "); if(mb.isSelected()){ test=ServiceChallenge.getInstance().finirChallenges(challenge,"87654321");System.out.println("selected ");} else {System.out.println("not selected  ");}} else {System.out.println("not a check box  ");}
            System.out.println("liste ");
            // if(mb.isCheckBox()){ System.out.println("check box  "); if(mb.isSelected()){ test=ServiceChallenge.getInstance().finirChallenges(challenge,"87654321");System.out.println("selected ");} else {System.out.println("not selected  ");}} else {System.out.println("not a check box  ");}

            //if(mb.isSelected()){ test=ServiceChallenge.getInstance().finirChallenges(challenge,"87654321");System.out.println("selected ");} else {System.out.println("not selected  ");}
            //  mb.isSelected(); chekbox selected 
        }

        this.add(CENTER, listeMC);
        this.show();

        Form formm = new ObjectifForm(username);
        

    }

}
