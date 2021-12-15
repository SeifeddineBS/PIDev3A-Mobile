
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.activiteTherapie;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Slider;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.notifications.LocalNotification;
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
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import aura.entities.Activites;
import aura.entities.Therapie;
import aura.entities.User;
import static aura.gui.activiteTherapie.Listact.user;
import aura.services.ServiceEvents;
import aura.services.ServiceUser;
import com.codename1.ui.Toolbar;
import java.util.ArrayList;

/**
 *
 * @author medimegh
 */
public class detailact extends Form {

    Activites r = new Activites();
     LocalNotification ln = new LocalNotification();
 public static User user;
    public static String username;
    TextArea tn;
    TextArea ts;
    TextArea tt;
    Form f = new Form("Activites");
    //SpanLabel lb = new SpanLabel("Liste :  ");
    Resources theme;

    public detailact(Activites iddet,Form previous, String username) {

        theme = UIManager.initFirstTheme("/theme");
          this.user = ServiceUser.getInstance().getUser(username);
        this.username = user.getId();


//        getToolbar().setTitleComponent(
//                FlowLayout.encloseCenterMiddle( //new Label("Recommendation", "Title")
//                        )
//        );
//
        //f.add(lb);
        Button up = new Button();
        int height = Display.getInstance().convertToPixels(50f);
        int width = Display.getInstance().convertToPixels(100f);
        ServiceEvents ser = new ServiceEvents();
        //  lb.setText(ser.rechercheSkill().toString());
        ArrayList<Activites> l = ser.getactivite(iddet.getId());
        for (int i = 0; i < l.size(); i++) {
            Label restaur = new Label("Restaurant" + l.get(i).toString());
            System.out.println("llllllllllllllll" + l);
            System.out.println( user.getId());
            r = l.get(i);

            Container c = new Container(BoxLayout.y());

         //   c.getStyle().setBorder(Border.createLineBorder(1));
            ImageViewer image = new ImageViewer();
            Container c1 = new Container(BoxLayout.x());
            tn = new TextArea("Lieu");
                  FloatingActionButton maps = FloatingActionButton.createFAB(FontImage.MATERIAL_MAP,"maps");
                              maps.addActionListener(e -> new MyApplication().start(f,username));
            Label l1 = new Label(r.getLieu());
            
            tn.setUIID("TextAreaBlack");
            c1.add(tn);
            c1.add(l1);c1.add(maps);

            Container c2 = new Container(BoxLayout.x());
            ts = new TextArea("Date");
            ts.setUIID("TextAreaBlack");
            Label l12 = new Label(r.getDate());
         //   Button detai = new Button("REjoindre");
                  FloatingActionButton detai = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);

            detai.addActionListener((e) -> {

                try {
                    System.out.println("in");
User u = new User();
                        u = ServiceUser.getInstance().getUser(username);
                        System.out.println(u.getId());
                    ser.rejoindreact(r.getId(),u.getId());
                    System.out.println(username);
                    System.out.println(r);
                        ToastBar.showMessage("Bienvenue", FontImage.MATERIAL_CELEBRATION);
            ln.setId("Activite");
            ln.setAlertTitle("Rappel");
            ln.setAlertBody("Vous avez une activite le "+r.getDate());
            Display.getInstance().scheduleLocalNotification(ln,System.currentTimeMillis() + 10 * 1000, LocalNotification.REPEAT_DAY);
                System.out.println("hiiiii");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });
                             FloatingActionButton notif = FloatingActionButton.createFAB(FontImage.MATERIAL_NOTIFICATIONS_OFF);
notif.addActionListener((e) -> {


Display.getInstance().cancelLocalNotification(ln.getId());
                   ToastBar.showMessage("Notification desactivé", FontImage.MATERIAL_NOTIFICATIONS_OFF);



});

            Slider jSlider = new Slider();
            jSlider.setMaxValue(5);
            jSlider.setMinValue(0);
            jSlider.setProgress(1);
            jSlider.setEditable(true);
            Label sli = new Label("note:");
             final Button btneval = new Button("Evaluer");

            jSlider.addDataChangedListener((type, index) -> {
                sli.setText(""+jSlider.getProgress()+"/5");
              
                
                
            });
            Container ci=new Container(new FlowLayout(CENTER,CENTER));
            Container cev = new Container(BoxLayout.x());

        ImageViewer img2=new ImageViewer(theme.getImage("portfolio-details-1.jpg"));
        ci.add(img2);
         c.add(ci);          
            btneval.addActionListener((e) -> {

                try {
                    System.out.println("in eval note");
User u = new User();
                        u = ServiceUser.getInstance().getUser(username);
                        System.out.println(u.getId());
                    ser.noteact(r.getId(),jSlider.getProgress(),u.getId());
                    System.out.println(r);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });

           
            c2.add(ts);
            c2.add(l12);

            Container c3 = new Container(BoxLayout.x());
            tt = new TextArea("Description");
            SpanLabel l3 = new SpanLabel(" " + r.getDescription());
            Label l4 = new Label("                                      " );

            tt.setUIID("TextAreaBlack");

            Therapie e = new Therapie();

            //like.setText(r.getQuantite()+ " disponibles");
            c3.add(tt);
            c3.add(l3);
            c3.add(l4);

            c.add(c1);
            c.add(c2);
            c.add(c3);
            c.add(detai);
 cev.add(jSlider);
  cev.add(sli);
  cev.add(btneval);
  c.add(cev);
                              Container c4 = new Container(BoxLayout.x());

      FloatingActionButton dislike = FloatingActionButton.createFAB(FontImage.MATERIAL_THUMB_DOWN_ALT);
                                FloatingActionButton like = FloatingActionButton.createFAB(FontImage.MATERIAL_THUMB_UP_ALT);
like.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e1) {
                    try {
                        System.out.println("in eval note");
                        User u = new User();
                        u = ServiceUser.getInstance().getUser(username);
                        System.out.println(u.getId());
                        ser.likeact(r.getId(),1,u.getId());
                        System.out.println(r);
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });


dislike.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e1) {
                    try {
                        System.out.println("in eval dislike");
                        User u = new User();
                        u = ServiceUser.getInstance().getUser(username);
                        System.out.println(u.getId());
                        ser.likeact(r.getId(),0,u.getId());
                        System.out.println(r);
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
                                      

                                
                                
                                

            f.add(c);
f.add( like);f.add(dislike); f.add(notif);

Toolbar tb = f.getToolbar();
            Form formm = new Listact(f,username);
            tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, evv->{
            
            previous.showBack();
            
            });
            f.show();
           
        }

    }

    

}
