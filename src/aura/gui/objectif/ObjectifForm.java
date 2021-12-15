/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.objectif;

import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import aura.entities.Objectif;
import aura.entities.User;
import aura.gui.HomeForm;

//Imports GUI
import aura.gui.gestionUser.HomeClient;
import aura.gui.challenge_classement.Classement;
import aura.gui.challenge_classement.ListChallengesForm;
import aura.gui.activiteTherapie.Listact;
import aura.gui.activiteTherapie.Listth;
import aura.gui.challenge_classement.MesChallenges;
import aura.gui.article.HomeArticle;
import aura.gui.entraide.AllQuestion;
// End 
import aura.services.ServiceObjectif;
import aura.services.ServiceUser;
import com.codename1.ui.FontImage;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 *
 * @author bhk
 */
public class ObjectifForm extends Form {

    Form current;
    private Resources theme;
    private Resources res;
    public static User user;
    public static String username;


    /*Garder traçe de la Form en cours pour la passer en paramètres 
    aux interfaces suivantes pour pouvoir y revenir plus tard en utilisant
    la méthode showBack*/
    
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
    
    
    public ObjectifForm(String username) {

        this.user = ServiceUser.getInstance().getUser(username);
        this.username = user.getId();
        System.out.println(this.username);
        sideBar(username);

        current = this; //Récupération de l'interface(Form) en cours
        theme = UIManager.initFirstTheme("/theme");
        setTitle("Mes Objectifs");
        setLayout(BoxLayout.y());
        Button btnAddObjectif = new Button("Ajouter");
        Button btnCaptureObjectif = new Button("Capture");
        Button btnStatObjectif = new Button("Stats");
        Button btnCalendar = new Button("Calendrier");

        Label backimg = new Label(theme.getImage("back.png"));
        String dateAuj = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        //Button btnListTasks = new Button("List Tasks");
        btnAddObjectif.addActionListener(e -> {
            new AddObjectifForm(current, dateAuj, username).show();

        });
        btnCaptureObjectif.addActionListener(e -> new CaptureObjectif(username).show());
        btnCalendar.addActionListener(e -> new CalendarForm(username).show());

        btnStatObjectif.addActionListener(e -> {
            StatObjectif sat = new StatObjectif();
            sat.execute(theme, username).show();

        });

        // btnListTasks.addActionListener(e -> new ShowObjectifForm(current).show());
        add(backimg);
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, btnAddObjectif, btnStatObjectif, btnCaptureObjectif, btnCalendar)
        ));
        //Affichge de la liste des objectifs
        List<Objectif> list = ServiceObjectif.getInstance().getAllObjectifs();
        for (Objectif e : list) {
            Container cnt1 = new Container(BoxLayout.y());
            Container cnt2 = new Container(BoxLayout.x());;

            SpanLabel lbdesc = new SpanLabel("Description :" + e.getDescription());
            SpanLabel lbrep = new SpanLabel("Réponse :" + e.getReponse());
            //Label lbimg = new Label(theme.getImage(e.getIcone()));
            Label img = new Label(theme.getImage(e.getIcone()));
            Button btnDetails = new Button("Détails");
            btnDetails.getStyle().setBgColor(0x99CCCC);
            cnt1.add(lbdesc);
            cnt1.add(lbrep);
            cnt1.add(btnDetails);
            cnt2.add(img);
            cnt2.add(cnt1);
            cnt2.getStyle().setBgColor(0x99CCCC);
            cnt2.getStyle().setBgTransparency(255);

            Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
            cnt2.getStyle().setBorder(RoundBorder.create().
                    rectangle(true).
                    color(0xffffff).
                    strokeColor(0).
                    strokeOpacity(120).
                    stroke(borderStroke));
            add(cnt2);
            btnDetails.addActionListener(p -> {
                ToastBar.Status status = ToastBar.getInstance().createStatus();
                ArrayList<String> affirmations = new ArrayList<>();
                affirmations.add("En me permettant d’être heureux, j’incite les autres à être heureux aussi.");
                affirmations.add("Le bonheur est mon droit imprescriptible. J’accepte que le bonheur devienne le cœur de mon existence.");
                affirmations.add("J’aime le changement et je m’adapte facilement aux nouvelles situations.");
                affirmations.add("Je m’accepte tel que je suis et je m’aime profondément et complètement.");
                affirmations.add("J’envoie de la paix dans le monde.");
                affirmations.add("Je dépasse le stress. Je vis en paix.");

                Random random = new Random();
                int nb;
                nb = random.nextInt(6);
                status.setMessage(affirmations.get(nb));
                status.show();
                try {
                    //...  Some time later you must clear the status
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
                status.clear();
                new DetailsObjectifForm(current, e, theme, username).show();
            }
            );

        }

    }
}
