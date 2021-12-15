
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.activiteTherapie;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import aura.entities.Activites;
import aura.entities.Therapie;
import aura.entities.User;
import aura.gui.HomeForm;
import aura.gui.article.HomeArticle;
import static aura.gui.objectif.AddObjectifForm.user;
import aura.gui.objectif.ObjectifForm;
import aura.services.ServiceEvents;
import aura.services.ServiceUser;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import java.util.ArrayList;
import static jdk.nashorn.internal.objects.NativeMath.random;

import aura.gui.gestionUser.HomeClient;
import aura.gui.challenge_classement.Classement;
import aura.gui.challenge_classement.ListChallengesForm;
import aura.gui.activiteTherapie.Listth;
import aura.gui.challenge_classement.MesChallenges;
import aura.gui.article.HomeArticle;
import aura.gui.entraide.AllQuestion;
import aura.gui.objectif.ObjectifForm;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Medimegh
 */
public class Listact extends Form {

    Activites r = new Activites();
    TextArea tn;
    TextArea ts;
    TextArea tt;
    public static User user;
    public static String username;

    Form current = new Form("Liste des Activites");
    Resources theme;

    public void sideBar(String username) {

        Toolbar tb = current.getToolbar();
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

    public Listact(Form previous, String username) {

        this.user = ServiceUser.getInstance().getUser(username);
        this.username = user.getId();
        sideBar(username);
        theme = UIManager.initFirstTheme("/theme");
//        getToolbar().setTitleComponent(
//                FlowLayout.encloseCenterMiddle( //new Label("Recommendation", "Title")
//                        )
//        );

        //f.add(lb);
        Button up = new Button();
        int height = Display.getInstance().convertToPixels(50f);
        int width = Display.getInstance().convertToPixels(100f);
        ServiceEvents ser = new ServiceEvents();
        //      getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> new HomeForm().showBack());

        ArrayList<Activites> l = ser.getAllact();

        List<Activites> list = ServiceEvents.getInstance().getAllact();

        for (Activites r : list) {
            Container c = new Container(BoxLayout.y());

            c.getStyle().setBorder(Border.createLineBorder(1));

            Container ci = new Container(new FlowLayout(CENTER, CENTER));
            Container cev = new Container(BoxLayout.x());

            ImageViewer img2 = new ImageViewer(theme.getImage("portfolio-details-1.jpg"));
            ci.add(img2);
            c.add(ci);
            ImageViewer image = new ImageViewer();
            Container c1 = new Container(BoxLayout.x());
            tn = new TextArea("Lieu");
            Label l1 = new Label(r.getLieu());
            tn.setUIID("TextAreaBlack");
            c1.add(tn);
            c1.add(l1);

            Container c2 = new Container(BoxLayout.x());
            ts = new TextArea("Date");
            ts.setUIID("TextAreaBlack");
            Label l12 = new Label(r.getDate());
            Button detai = new Button("Details");
            detai.addActionListener(e -> new detailact(r, current, username));

            c2.add(ts);
            c2.add(l12);

            Container c3 = new Container(BoxLayout.x());
            tt = new TextArea("Sujet");
            Label l3 = new Label(" " + r.getDescription());
            tt.setUIID("TextAreaBlack");

            Therapie e = new Therapie();

            c3.add(tt);
            c3.add(l3);
            c3.add(detai);

            //c.add(c1);
            // c.add(c2);
            c.add(c3);

            current.add(c);
//Toolbar tb = f.getToolbar();
//            Form formm = new Listact(f,username);
//            tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, evv->{
//            
//            previous.showBack();
//            
//            });
            current.show();

        }

    }

}
