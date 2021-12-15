/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.objectif;

import com.codename1.components.SpanLabel;
import com.codename1.messaging.Message;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import aura.entities.Objectif;
import aura.entities.User;
import static aura.gui.objectif.CalendarForm.user;
import aura.services.ServiceObjectif;
import aura.services.ServiceUser;
import java.util.List;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 *
 * @author Chirine
 */
public class DetailsObjectifForm extends Form {
        public static User user;
    public static String username;

    Form current;

    public DetailsObjectifForm() {

    }

    public DetailsObjectifForm(Form previous, Objectif e, Resources theme, String username) {
                this.user = ServiceUser.getInstance().getUser(username);
        this.username = user.getId();
        LocalNotification n = new LocalNotification();
        n.setId("demo-notification");
        n.setAlertBody("It's time to take a break and look at me");
        n.setAlertTitle("Break Time!");
        n.setAlertSound("/notification_sound_bells.mp3"); //file name must begin with notification_sound
        Display.getInstance().scheduleLocalNotification(n, System.currentTimeMillis() + 10 * 1000, LocalNotification.REPEAT_NONE);
        System.out.println(System.currentTimeMillis());
        setTitle("Details de l'objectif ");
        SpanLabel lbdesc = new SpanLabel();
        Label lbrep = new Label();
        Label lbduree = new Label();
        Label lbdate = new Label();
        Button btnModifierObj = new Button("Modifier cet Objectif");
        Button btnSupprimerObj = new Button("Supprimer cet Objectif");
        Button btnMail = new Button("Rappel par mail");

        Toolbar.setGlobalToolbar(true);
        Style bg = this.getContentPane().getUnselectedStyle();
        // bg.setBgTransparency(255);
        //bg.setBgColor(0xff0000);
        TextField duration = new TextField("10000", "Duration", 6, TextArea.NUMERIC);
        duration.setText("3000");
        Form dest = new ModifierObjectifForm(current, e,username);
        dest.setBackCommand(
                dest.getToolbar().addCommandToLeftBar("Back", null, (x) -> dest.show()));

        btnModifierObj.addActionListener((y) -> {
            int h = CommonTransitions.SLIDE_HORIZONTAL;
            this.setTransitionOutAnimator(CommonTransitions.createFade(duration.getAsInt(3000)));
            dest.setTransitionOutAnimator(CommonTransitions.createFade(duration.getAsInt(3000)));

            dest.show();
        });

        // btnModifierObj.addActionListener(p -> new ModifierObjectifForm(current, e).show());
        btnSupprimerObj.addActionListener(p -> ServiceObjectif.getInstance().SupprimerObjectif(e));
        btnMail.addActionListener((p) -> {
            Message m = new Message("N'oubliez pas que vous avez l'objectif: " + e.getDescription() + " à terminer");
            m.getAttachments().put("hh", "text/plain");
            m.getAttachments().put("hh", "image/png");
            Display.getInstance().sendMessage(new String[]{"chirinenasri13@gmail.com"}, "AURA: Rappel", m);

        });

        addAll(lbdesc, lbrep, lbduree, lbdate, btnModifierObj, btnSupprimerObj, btnMail);

        lbdesc.setText("Description :" + e.getDescription());
        lbrep.setText("Réponse :" + e.getReponse());
        lbduree.setText("Durée :" + e.getDuree());
        lbdate.setText("Date de debut :" + e.getDate());

        show();

        getToolbar().addCommandToLeftBar("back", null, ev -> {
            previous.show();
        });

    }

}
