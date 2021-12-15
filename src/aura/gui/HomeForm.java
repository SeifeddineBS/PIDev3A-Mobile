/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui;

import aura.gui.gestionUser.forgetPasswordCheck;
import aura.gui.gestionUser.AddClientForm;
import aura.gui.gestionUser.facebookLogin;
import aura.gui.gestionUser.VerificationCodeSms;
import aura.entities.User;
import aura.gui.objectif.ObjectifForm;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import aura.services.ServiceUser;
import aura.utils.Statics;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author bhk
 */
public class HomeForm extends Form {

    Form current;
    public static String username = "";
    public static String verificationCode;
    public static Media music = null;

    public void musicPlay() {
        ArrayList<String> musiqueArray = new ArrayList<String>();
        musiqueArray.add("music0.mp3");
        musiqueArray.add("music1.mp3");
        musiqueArray.add("music2.mp3");

        String directory = "file://C:/Users/SeifBS/Downloads/";

        int random = new Random().nextInt(3);
        System.out.println("random:" + random);

        try {
            this.music = MediaManager.createMedia(directory + musiqueArray.get(random), false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.music.play();

    }

    public Label showLogo() throws IOException {
        Image image = Image.createImage(Statics.directory + "logo.png");
        int w = image.getWidth();
        int h = image.getHeight();
        Image maskImage = Image.createImage(w, h);
        Graphics g = maskImage.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0x000000);
        g.fillRect(0, 0, w, h);
        g.setColor(0xffffff);
        g.fillArc(0, 0, w, h, 0, 360);
        Object mask = maskImage.createMask();
        Image maskedImage = image.applyMask(mask);
        Label picture = new Label(maskedImage);
        return picture;

    }

    public HomeForm() throws IOException {
        musicPlay();

        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Acceuil");
        setLayout(BoxLayout.y());

        Button createAccount = new Button("Inscription");
        FontImage.setMaterialIcon(createAccount, FontImage.MATERIAL_CREATE);
        Button seConnecter = new Button("Se connecter");
        FontImage.setMaterialIcon(seConnecter, FontImage.MATERIAL_LOGIN);
        TextField email = new TextField("", "Adresse Email");
        TextField password = new TextField("", "Mot de passe");
        Button forgetPassword = new Button("mot de passe oublié");
        Button seConnecterFacebook = new Button();
        Button capture = new Button("");
        FontImage.setMaterialIcon(capture, FontImage.MATERIAL_PHOTO_CAMERA);
        seConnecterFacebook.addActionListener((e) -> {
            facebookLogin facebookLogin;
            try {
                facebookLogin = new facebookLogin(current);
                facebookLogin.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        password.setConstraint(TextField.PASSWORD);

        seConnecter.addActionListener((e) -> {

            String result = ServiceUser.getInstance().loginCheck(email.getText(), password.getText());

            if ("true".equals(result)) {
                User user = new User();
                this.username = email.getText();
                user = ServiceUser.getInstance().getUser(this.username);
                if (user.getSms().equals("Y")) {
                    Random random = new Random();
                    this.verificationCode = valueOf(random.nextInt()).substring(1, 6);
                    // Boolean sendSmsMethod = ServiceUser.getInstance().sendSms(user.getId(),this.verificationCode);
                    System.out.println(this.verificationCode);
                    Dialog.show("Verification", "Veuillez verifier votre telephone", new Command("OK"));

                    new VerificationCodeSms(current, this.username).show();
                } else {

                    
//                    Dialog d = new Dialog("Bienvenue");
//                    TextArea popupBody = new TextArea("Salut " + user.getNom().toUpperCase() + " " + user.getPrenom().toUpperCase() + " L'equipe Aura est heureuse de te souhaiter la bienvenue.", 3, 20);
//                    popupBody.setUIID("PopupBody");
//                    popupBody.setEditable(false);
//                    d.setLayout(new BorderLayout());
//                    d.add(BorderLayout.CENTER, popupBody);

                    new ObjectifForm(this.username).show();

                }
            } else {
                Dialog.show("Attention", "Veuillez verifier vos données ", new Command("OK"));

            }

        });

        //e -> new AddTaskForm(current).show()
        //  seConnecter.addActionListener(e -> new ListTasksForm(current).show());
        createAccount.addActionListener((e) -> {

            new AddClientForm(current).show();

        });
        capture.addActionListener((e) -> {
            Image screenshot = Image.createImage(this.getWidth(), this.getHeight());
            this.revalidate();
            this.setVisible(true);
            this.paintComponent(screenshot.getGraphics(), true);
            String directory = "file://C:/Users/SeifBS/Documents/";
            String imageFile = directory + "screenshot" + valueOf(new Random().nextInt()).substring(1, 6) + ".png";
            //FileSystemStorage.getInstance().getAppHomePath().
            System.out.println(imageFile);
            try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
            } catch (IOException err) {
                Log.e(err);
            }
        });

        forgetPassword.addActionListener((e) -> {

            new forgetPasswordCheck(current).show();

        });

        Container containerLigne1 = new Container(BoxLayout.x());
        containerLigne1.add(seConnecter);
        containerLigne1.add(createAccount);

        //addAll(email, password, seConnecter, createAccount, forgetPassword, capture);
        //    add(BorderLayout.NORTH,capture);
        Container ContainerCaptureFacebook = BorderLayout.south(new Label());
        ContainerCaptureFacebook.add(BorderLayout.EAST, capture);
        ContainerCaptureFacebook.add(BorderLayout.WEST, seConnecterFacebook);
        FontImage.setMaterialIcon(seConnecterFacebook, FontImage.MATERIAL_FACEBOOK);

        Container containerLogo = BorderLayout.centerAbsolute(new Label());

        Label picture = showLogo();
        containerLogo.add(BorderLayout.CENTER_BEHAVIOR_SCALE, new Label("                        "));
        containerLogo.add(BorderLayout.CENTER_BEHAVIOR_SCALE, picture);

        add(containerLogo);

        addAll(email, password, containerLigne1, forgetPassword, ContainerCaptureFacebook);

    }

    public String getPictureTitle(String path) {
        System.out.println("hey" + path);
        int i;
        String pictureTitle = "";
        for (i = path.length(); i >= 0; i--) {

            if (path.charAt(i) == "/".charAt(0)) {
                return pictureTitle;
            }
            pictureTitle += path.charAt(i);
        }
        System.out.println("hey" + path.charAt(i));
        return pictureTitle;
    }

}
