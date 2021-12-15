
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.activiteTherapie;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Slider;

import com.codename1.components.ImageViewer;
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
import aura.entities.Therapie;
import aura.entities.User;
import static aura.gui.activiteTherapie.detailact.user;
import aura.services.ServiceEvents;
import aura.services.ServiceUser;
import com.codename1.ui.Toolbar;
import java.util.ArrayList;

/**
 *
 * @author medimegh
 */
public class detailtherapie extends Form {

    Therapie r = new Therapie();
    LocalNotification ln = new LocalNotification();
          public static User user;
    public static String username;
    TextArea tn;
    TextArea ts;
    TextArea tt;
    Form f = new Form("Therapie");
    //SpanLabel lb = new SpanLabel("Liste :  ");
    
    Resources theme;

    public detailtherapie(Therapie iddet,Form previous, String username) {
     
  this.user = ServiceUser.getInstance().getUser(username);
        this.username = user.getId();
 
        theme = UIManager.initFirstTheme("/theme");
        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle( //new Label("Recommendation", "Title")
                        )
        );

        //f.add(lb);
        Button up = new Button();
        int height = Display.getInstance().convertToPixels(50f);
        int width = Display.getInstance().convertToPixels(100f);
        ServiceEvents ser = new ServiceEvents();
        //  lb.setText(ser.rechercheSkill().toString());
        ArrayList<Therapie> l = ser.gettherapie(iddet.getId());
        for (int i = 0; i < l.size(); i++) {
            System.out.println(r.toString());
            Label restaur = new Label("Restaurant" + l.get(i).toString());
            System.out.println("llllllllllllllll" + l);

            r = l.get(i);

            Container c = new Container(BoxLayout.y());

         //   c.getStyle().setBorder(Border.createLineBorder(1));
            ImageViewer image = new ImageViewer();
            Container c1 = new Container(BoxLayout.x());
            tn = new TextArea("Lieu");
            Label l1 = new Label(r.getLieu());
               FloatingActionButton maps = FloatingActionButton.createFAB(FontImage.MATERIAL_MAP);
                              maps.addActionListener(e -> new MyApplication().start(f,username));
            tn.setUIID("TextAreaBlack");
            c1.add(tn);
            c1.add(maps);
            c1.add(l1);

            Container c2 = new Container(BoxLayout.x());
            ts = new TextArea("Date");
            ts.setUIID("TextAreaBlack");
            Label l12 = new Label(r.getDate());
         //   Button detai = new Button("REjoindre");
                  FloatingActionButton detai = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);

            detai.addActionListener((e) -> {

                try {
                    System.out.println("in");
  ln.setId("Activite");
            ln.setAlertTitle("Rappel");
            ln.setAlertBody("Vous avez une therapie le "+r.getDate());
            User u = new User();
                        u = ServiceUser.getInstance().getUser(username);
                        System.out.println(u.getId());
                    ser.rejoindre(r.getId(),u.getId());
                    System.out.println(u.getId());
                    System.out.println(r);
                   ToastBar.showMessage("Bienvenue", FontImage.MATERIAL_CELEBRATION);
 
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
                    ser.noteth(r.getId(),jSlider.getProgress(),u.getId());
                    System.out.println(r);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });
                        Button detail = new Button("REjoindre");

           
            c2.add(ts);
            c2.add(l12);

            Container c3 = new Container(BoxLayout.x());
            tt = new TextArea("Sujet");
            Label l3 = new Label(" " + r.getSujet());
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
                        System.out.println("in like");
                        User u = new User();
                        u = ServiceUser.getInstance().getUser(username);
                        System.out.println(u.getId());
                        ser.liketh(r.getId(),1,u.getId());
                        System.out.println(r);
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });


dislike.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e1) {
                    try {User u = new User();
                        u = ServiceUser.getInstance().getUser(username);
                        System.out.println(u.getId());
                        System.out.println("in eval dislike");
                        
                        ser.liketh(r.getId(),0,u.getId());
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
//  
//Form formm = new Listact(f,username);
//       f.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
//                 e1 -> formm.showBack());
            f.show();
            
        }
        
      // f.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new Listact(f,username).showBack());

    }

    

}
