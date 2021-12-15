/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.entraide;

/**
 *
 * @author Nour Dekhil
 */
import com.codename1.components.MultiButton;
import com.codename1.location.GeofenceListener;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import aura.entities.Question;
import aura.entities.User;
import aura.services.QuestionService;
import aura.services.ReponseService;
import aura.services.ServiceUser;

import java.util.List;

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
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import java.io.IOException;

//End 

/**
 *
 * @author Nour Dekhil
 */
public class AllQuestion extends Form {

    Form current;
    String username;
    User user;
    private Resources theme;
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
                new HomeClient(username);
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
    public AllQuestion(String username) {
        
        
        this.username = username;
        this.user = ServiceUser.getInstance().getUser(this.username);
        sideBar(username);

        current = this; //Récupération de l'interface(Form) en cours
        theme = UIManager.initFirstTheme("/theme");

        setLayout(BoxLayout.y());
        setTitle("Liste des Questions");
        List<Question> list = QuestionService.getInstance().showQuestion();

        getToolbar().addSearchCommand(es -> {
            String text = (String) es.getSource();
            text = text.toLowerCase();
            for (Question e : list) {

                getContentPane().animateLayout(150);
                Label lbnom = new Label("Question :" + e.getQuestion());
                Label lbcat = new Label("Categorie :" + e.getCategorie());
                Container cnt1 = new Container(BoxLayout.y());
                Container cnt = new Container(BoxLayout.x());
                Button btn = new Button("Detail ");
                Button btn2 = new Button("Modifier ");
                Button btn3 = new Button("Supprimer ");
                cnt.add(btn);
                cnt.add(btn2);
                cnt.add(btn3);
                cnt1.add(lbnom);
                cnt1.add(lbcat);
                cnt1.add(cnt);
                String line1 = e.getQuestion();
                boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1;
                cnt1.setHidden(!show);
                cnt1.setVisible(show);

                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        new QuestionDetail(current, e,username).show();
                    }
                });

                btn3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Dialog d = new Dialog("Suppression");

                        if (d.show("Suppression", "Vous voulez vraiment supprimer ce reponse?", "Annuler", "oui")) {
                            d.dispose();
                        } else {
                            d.dispose();
                            if (QuestionService.getInstance().SupprimerQuestion(e.getId())) {
                                new AllQuestion(username).show();
                            }
                        }
                    }
                });

                btn2.addPointerPressedListener(l -> {
                    TextField la = new TextField("", "new Question");
                    TextField la1 = new TextField("", "new Categorie");
                    Button b = new Button("Update");
                    Dialog d = new Dialog();
                    d.add(la);
                    d.add(la1);
                    d.add(b);

                    b.addPointerPressedListener(a -> {

                        if (QuestionService.getInstance().UpdateQuestion(e.getId(), la.getText(), la1.getText())) {
                            d.dispose();
                        }
                        new AllQuestion(username).show();

                    });
                    d.show();
                });

                add(cnt1);
            }

        });

        getToolbar().addCommandToLeftBar("add", null, ec -> {
            Dialog dial = new Dialog();
            Label lab = new Label("Poser un question ");
            TextField tfQ = new TextField("", "question");
            TextField tfC = new TextField("", "categorie");
            Button BTN = new Button("Add");
            dial.add(lab);
            dial.add(tfQ);
            dial.add(tfC);
            dial.add(BTN);
            BTN.addPointerPressedListener(q -> {
                if (QuestionService.getInstance().addQuestion(tfQ.getText(), tfC.getText(),this.user.getId())) {
                    dial.dispose();
                }
            });

            dial.show();
        });
    }

}
