/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.gestionUser;

import com.codename1.capture.Capture;
import com.codename1.io.File;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import aura.entities.User;
import aura.services.SendMail;
import aura.services.ServiceUser;
import aura.services.Validator;
import static java.lang.String.valueOf;
import java.util.Random;

/**
 *
 * @author bhk
 */
public class AddClientForm extends Form {

    String picture = "default.jpg";
    Form current;
    public static String verificationCode;

    public AddClientForm(Form previous) {
        current = this;
        Validator v = new Validator();
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
         */
        setTitle("Creer un compte ");
        setLayout(BoxLayout.y());

        TextField id = new TextField("", "Cin");
        TextField nom = new TextField("", "Nom");
        TextField prenom = new TextField("", "Prenom");
        TextField email = new TextField("", "Email");
        TextField tel = new TextField("", "Numero");
        TextField password = new TextField("", "Mot de passe");
        TextField confirmer_password = new TextField("", "Confirmer mot de passe");
        Button uploadPicture = new Button("Ajouter Votre photo");

        password.setConstraint(TextField.PASSWORD);
        confirmer_password.setConstraint(TextField.PASSWORD);

        Button btnValider = new Button("Creer compte");

        uploadPicture.addActionListener((ActionEvent e) -> {
            try {
                String imgPath = Capture.capturePhoto();
                String link = imgPath.toString();
                int pod = link.indexOf("/", 4);
                String news = link.substring(pod + 37, link.length());
                this.picture = news;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        btnValider.addActionListener((e) -> {
            Random random = new Random();
            User u = new User(id.getText(), nom.getText(), prenom.getText(), email.getText(), password.getText(), tel.getText(), this.picture);
            String result = ServiceUser.getInstance().checkUserUnique(u);
            System.out.println(result);

            if ((nom.getText().length() == 0) || (prenom.getText().length() == 0)) {
                Dialog.show("Attention", "Veuillez verifier vos données ", new Command("OK"));
            } else if (!v.test_Email(email.getText())) {
                Dialog.show("Attention", "Veuillez verifier votre email", new Command("OK"));
            } else if (!v.test_Cin(id.getText())) {
                Dialog.show("Attention", "Veuillez verifier votre Carte d'identite", new Command("OK"));
            } else if (!v.test_Tel(tel.getText())) {
                Dialog.show("Attention", "Veuillez verifier votre numero telephonique", new Command("OK"));
            } else if (!v.test_Password(password.getText())) {
                Dialog.show("Attention", "Votre passe doit contenir majuscule un chiffre et une miniscule au minimum", new Command("OK"));
            } else if (!password.getText().equals(confirmer_password.getText())) {
                Dialog.show("Attention", "Les deux mot de passes ne correspondent pas", new Command("OK"));
            } else {

                if ("-1".equals(result)) {
                    Dialog.show("Attention", "Cin existe deja", new Command("OK"));
                } else if ("-2".equals(result)) {
                    Dialog.show("Attention", "Email existe deja", new Command("OK"));
                } else if ("1".equals(result)) {
                    String verificationCode = valueOf(random.nextInt()).substring(1, 6);
                    this.verificationCode = verificationCode;

                    SendMail sendMail = new SendMail();
                    try {
                        sendMail.send(email.getText(), "Verification du compte", "Voice votre code de verification" + verificationCode);
                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                    Dialog.show("Verification", "Veifier votre courrier", new Command("OK"));
                    new VerificationCodeAddAccount(current, u).show();
                    //Dialog.show("Success", "Compte crée avec succès", new Command("OK"));
                } else {
                    Dialog.show("ERROR", "Server error", new Command("OK"));
                }

            }

        });

        addAll(id, nom, prenom, email, tel, password, confirmer_password, uploadPicture, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                e -> previous.showBack()); // Revenir vers l'interface précédente

    }

}
