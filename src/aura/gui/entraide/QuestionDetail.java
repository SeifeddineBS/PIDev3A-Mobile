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
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.util.Base64;
import aura.entities.Question;
import aura.entities.Reponse;
import aura.entities.User;
import aura.services.QuestionService;
import aura.services.ReponseService;
import aura.services.ServiceUser;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import com.codename1.ui.layouts.BorderLayout;
import java.io.IOException;

//End 

/**
 *
 * @author Nour Dekhil
 */
public class QuestionDetail extends Form {

    Form current;
    String username;
    User user;
    private Resources theme;

    public QuestionDetail() {

    }
    
     public void sideBar()
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

    public QuestionDetail(Form previous, Question q, String username) {
       

        current = this; //Récupération de l'interface(Form) en cours
         this.username = username;
        this.user = ServiceUser.getInstance().getUser(this.username);
        sideBar();
        theme = UIManager.initFirstTheme("/theme");
        List<Reponse> list = ReponseService.getInstance().showReply(q.getId());
        System.out.println("rep" + q.getId());
        setLayout(BoxLayout.y());
        setTitle("Question");

        Container cnt1 = new Container(BoxLayout.y());
        Label qq = new Label("Question");
        Label lbnom = new Label("Question :" + q.getQuestion());
        Label lbcat = new Label("Categorie :" + q.getCategorie());

        Label r = new Label("Réponse ");
        cnt1.add(qq);

        cnt1.add(lbnom);
        cnt1.add(lbcat);

        cnt1.add(r);
        for (Reponse e : list) {

            Container cnt = new Container(BoxLayout.y());
            Container cnt2 = new Container(BoxLayout.x());
            Label lb = new Label("rep :" + e.getReponse());
            Label lSupprimer = new Label("");
            lSupprimer.setUIID("NewsTopLine");
            Style suppStyle = new Style(lSupprimer.getUnselectedStyle());
            FontImage supprimeImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, suppStyle);
            lSupprimer.setIcon(supprimeImage);
            lSupprimer.setTextPosition(RIGHT);

            lSupprimer.addPointerPressedListener(l -> {
                Dialog d = new Dialog("Suppression");

                if (d.show("Suppression", "Vous voulez vraiment supprimer ce reponse?", "Annuler", "oui")) {
                    d.dispose();
                } else {
                    d.dispose();
                    if (ReponseService.getInstance().SupprimerObjectif(e.getId())) {
                        new QuestionDetail(previous, q,this.username).show();
                    }
                }
            });
            Label lModifier = new Label("");
            lModifier.setUIID("NewsTopLine");
            Style modStyle = new Style(lModifier.getUnselectedStyle());
            FontImage modImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modStyle);
            lModifier.setIcon(modImage);
            lModifier.setTextPosition(RIGHT);

            cnt.add(cnt2);

            cnt2.add(lb);
            cnt2.add(lModifier);
            lModifier.addPointerPressedListener(l -> {
                TextField la = new TextField("", "new reply");
                Button b = new Button("Update");
                Dialog d = new Dialog();
                d.add(la);
                d.add(b);

                b.addPointerPressedListener(a -> {

                    if (ReponseService.getInstance().UpdateReply(e.getId(), la.getText())) {
                        d.dispose();
                    }
                    new QuestionDetail(previous, q,this.username).show();

                });
                d.show();
            });

            cnt2.add(lSupprimer);

            cnt1.add(cnt);
        }
        Container cnt3 = new Container(BoxLayout.y());
        TextField addrep = new TextField("", "reponse"); // a changeeeer!!!!!!!!!!!

        Button btnValider = new Button("Répondre");

        btnValider.addActionListener(e -> {
            if (addrep.getText() == "") {

                Dialog.show("Alerte", "Veuillez remplir tous les champs", new Command("OK"));

            } else {
                if (ReponseService.getInstance().addReply(q.getId(), addrep.getText(),this.user.getId())) {
                    new QuestionDetail(previous, q,this.username).show();
                }
            }
        });

        cnt3.add(addrep);
        cnt3.add(btnValider);
        cnt1.add(cnt3);

        add(cnt1);

        getToolbar().addCommandToLeftBar("back", null, ev -> {
            previous.show();
        });

    }

}
