/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.objectif;

import aura.entities.User;
import static aura.gui.objectif.AddObjectifForm.user;
import aura.services.ServiceUser;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Chirine
 */
public class CalendarForm extends Form {
      public static User user;
    public static String username;

    public CalendarForm(String username) {
        this.user = ServiceUser.getInstance().getUser(username);
        this.username = user.getId();
        setTitle("Mon agenda");

        Form formm = new ObjectifForm(username);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> formm.showBack());
        Form f = new ObjectifForm(username);
        Container cnt = new CalendrierObj(f, username);
        add(cnt);

    }

}
