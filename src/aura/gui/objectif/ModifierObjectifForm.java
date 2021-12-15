/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.objectif;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.animations.FlipTransition;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import aura.entities.User;
import aura.entities.Objectif;
import static aura.gui.objectif.CalendarForm.user;
import aura.services.ServiceObjectif;
import aura.services.ServiceUser;

/**
 *
 * @author Chirine
 */
public class ModifierObjectifForm extends Form {

    public static User user;
    public static String username;

    public ModifierObjectifForm(Form previous, Objectif o, String username) {
        this.user = ServiceUser.getInstance().getUser(username);
        this.username = user.getId();
        setTitle("Modifier l'objectif");
        setLayout(BoxLayout.y());

        TextField tfDesc = new TextField("", "Description");
        TextField tfRep = new TextField("", "Réponse");
        TextField tfDateDebut = new TextField("", "Date de début");
        TextField tfDuree = new TextField("", "Duree");
        // TextField tfAdmin = new TextField("", "ID Admin"); // a changeeeer!!!!!!!!!!!

        tfDesc.setText(o.getDescription());
        tfRep.setText("" + o.getReponse());
        tfDateDebut.setText(o.getDate());
        tfDuree.setText("" + o.getDuree());
        //  tfAdmin.setText(o.getCli().getId());

        Button btnValider = new Button("Confirmer");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfDesc.getText().length() == 0) || (tfRep.getText().length() == 0) || (tfDateDebut.getText().length() == 0) || (tfDuree.getText().length() == 0)) {
                    Dialog.show("Alerte", "Veuillez remplir tous les champs", new Command("OK"));
                } else {
                    try {
                        Objectif t = new Objectif();
                        t.setId(o.getId());
                        t.setDescription(tfDesc.getText());
                        t.setReponse(Integer.parseInt(tfRep.getText()));
                        t.setDate(tfDateDebut.getText());
                        t.setDuree(Integer.parseInt(tfDuree.getText()));

                        User u = new User();
                        u = ServiceUser.getInstance().getUser(username);
                        if (ServiceObjectif.getInstance().modifierObjectif(t)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }

            }
        });

        addAll(tfDesc, tfRep, tfDateDebut, tfDuree, btnValider);
        Form formm = new ObjectifForm(username);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> formm.showBack());

    }

    private void setLayout(BoxLayout y) {
    }

}
