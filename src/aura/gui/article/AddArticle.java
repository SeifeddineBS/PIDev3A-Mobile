/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.article;

import aura.entities.User;
import aura.entities.article;
import aura.gui.HomeForm;
import aura.gui.activiteTherapie.Listact;
import aura.gui.activiteTherapie.Listth;
import aura.gui.objectif.ObjectifForm;
import aura.services.ServiceArticle;
import aura.services.ServiceUser;
import com.codename1.components.SpanLabel;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import static com.codename1.util.Base64.encodeNoNewline;
import java.io.InputStream;
import java.util.Calendar;


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
 * @author akram
 */
public class AddArticle extends Form {
Form current ;
    String data;

    String username;
    User user;
    
    
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

    public AddArticle(String username) {
        this.username = username;
        sideBar(username);
        this.user = ServiceUser.getInstance().getUser(this.username);
        
                
       
        TextField titre = new TextField("", "titre");
        TextField theme = new TextField("", "theme");
        TextField auteur = new TextField("", "auteur");
        //TextField date = new TextField("", "date");
        //TextField article = new TextField("", "article");
        Button filechooser = new Button("article");
        filechooser.addActionListener(e -> {

        if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".pdf", e2 -> {
                    String file = (String) e2.getSource();
                    if (file == null) {
                        this.add("No file was selected");
                        this.revalidate();
                    } else {
                        try {
                            
                           
                             data = encodeNoNewline(Util.readInputStream(FileSystemStorage.getInstance().openInputStream(file)));
                            //se.send(data);                       
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        
        
        
        });
            Button btnValide = new Button("Creer ");

            btnValide.addActionListener(
                    new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent evt
                        ) {
                            if ((titre.getText().length() == 0) || (theme.getText().length() == 0) || (auteur.getText().length() == 0)) {
                                Dialog.show("Attention", "Veuillez verifier vos données ", new Command("OK"));
                            } else {
                                try {
                                    //User u = new User(Integer.parseInt(tfStatus.getText()), tfName.getText());
                                    article t = new article();
                                    t.setId(55);
                                    t.setTitre(titre.getText());
                                    t.setTheme(theme.getText());
                                    t.setDate(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
                                    t.setNom_auteur(auteur.getText());

                                    //t.setArticle(article.getText());
                                    t.setId_user(user.getId());
                                    t.setApprouver(0);

                                    if (ServiceArticle.getInstance().addarticle(t, data)) {
                                        Dialog.show("Success", "Article crée avec succès", new Command("OK"));
                                        LocalNotification ln = new LocalNotification();
                                        ln.setId("LnMessage");
                                        ln.setAlertTitle("felicitation");
                                        ln.setAlertBody("vous avez ajouté un article!");
                                        Display.getInstance().scheduleLocalNotification(ln, System.currentTimeMillis(), LocalNotification.REPEAT_NONE);

                                    } else {
                                        Dialog.show("ERROR", "Server error", new Command("OK"));
                                    }
                                } catch (NumberFormatException e) {
                                    Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                                }

                            }

                        }
                    }
            );

            addAll(titre, theme, auteur, filechooser, btnValide);
                    getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev-> new HomeArticle(username).showBack());

//getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev -> new HomeArticle().showBack());
        }
    
}
