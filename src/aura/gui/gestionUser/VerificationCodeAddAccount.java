/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.gestionUser;

import aura.entities.User;
import aura.gui.HomeForm;
import aura.services.ServiceUser;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import aura.services.Validator;
import java.io.IOException;
import static java.lang.String.valueOf;
import java.util.Random;

/**
 *
 * @author SeifBS
 */
public class VerificationCodeAddAccount extends Form {

    Form current;
    public int tentative = 0;
    public String verificationCode;

    public VerificationCodeAddAccount(Form previous, User u) {

        current = this;
        setTitle("Code de verification");
        Validator v = new Validator();

        TextField verificationCodeField = new TextField("", "Code de verification");
        Button verifier = new Button("verifier");
        Random random = new Random();
        this.verificationCode = AddClientForm.verificationCode;

        verifier.addActionListener((e) -> {

            if (!verificationCodeField.getText().equals(this.verificationCode)) {
                tentative++;
                Dialog.show("Attention", "Code incorrect", new Command("OK"));

                if (tentative > 2) {
                    Dialog.show("Erreur", "Vous avez atteint toutes les tentatives ", new Command("OK"));
                    try {
                        new HomeForm().show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            } else {
                String result = ServiceUser.getInstance().addUser(u);
                Dialog.show("Success", "Compte crée avec succès", new Command("OK"));
                try {
                    new HomeForm().show();
                } catch (IOException ex) {
                    ex.printStackTrace();

                }
            }

        });

        addAll(verificationCodeField, verifier);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
