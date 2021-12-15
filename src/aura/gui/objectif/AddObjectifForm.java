/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.objectif;

import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import aura.entities.Objectif;
import aura.entities.User;
import aura.services.ServiceObjectif;
import aura.services.ServiceUser;

/**
 *
 * @author Chirine
 */
public class AddObjectifForm extends Form {

    public static User user;
    public static String username;

    public AddObjectifForm(Form previous, String datee, String username) {

        this.user = ServiceUser.getInstance().getUser(username);
        this.username = user.getId();

        setTitle("Ajouter un objectif");
        setLayout(BoxLayout.y());

        TextField tfDesc = new TextField("", "Description");
        TextField tfRep = new TextField("", "Réponse");
        TextField tfDateDebut = new TextField("", "Date de début");
        tfDateDebut.setText(datee);
        TextField tfDuree = new TextField("", "Duree");

        Button btnValider = new Button("Confirmer");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfDesc.getText().length() == 0) || (tfRep.getText().length() == 0) || (tfDateDebut.getText().length() == 0) || (tfDuree.getText().length() == 0)) {
                    Dialog.show("Alerte", "Veuillez remplir tous les champs", new Command("OK"));
                } else {
                    try {
                        Objectif t = new Objectif();
                        t.setDescription(tfDesc.getText());
                        t.setReponse(Integer.parseInt(tfRep.getText()));
                        t.setDate(tfDateDebut.getText());
                        t.setDuree(Integer.parseInt(tfDuree.getText()));

                        User u = new User();
                        u = ServiceUser.getInstance().getUser(username);

                        t.setCli(u);
                        if (ServiceObjectif.getInstance().addObjectif(t)) {
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
        show();

    }

    private void setLayout(BoxLayout y) {
    }

}
