/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.gestionUser;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.facebook.FaceBookAccess;
import aura.entities.User;
import aura.services.ServiceUser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Map;

/**
 *
 * @author SeifBS
 */
public class facebookLogin extends Form {

    private Login login;
    Form current;
    public static String username;
    public User user;

    public facebookLogin() {

    }

    public facebookLogin(Form previous) throws IOException {

        Button logout = new Button("logout");
        add(logout);

        logout.addActionListener((e) -> {

            String clientId = "2822192241425724";
            String redirectURI = "http://localhost/"; //Una URI cualquiera. Si la pones en tu equipo debes crear un Servidor Web. Yo usé XAMPP
            String clientSecret = "0eba35b54a918564e867efc3aee9d8e2";
            Login fb = FacebookConnect.getInstance();
            fb.setClientId(clientId);
            fb.setRedirectURI(redirectURI);
            fb.setClientSecret(clientSecret);

            //trigger the login if not already logged in
            fb.doLogout();
            Storage.getInstance().writeObject("token", "");

        });
        current = this;

        String clientId = "2822192241425724";
        String redirectURI = "http://localhost/";
        String clientSecret = "0eba35b54a918564e867efc3aee9d8e2";

        if (clientSecret.length() == 0) {
            System.err.println("create your own facebook app follow the guide here:");
            System.err.println("http://www.codenameone.com/facebook-login.html");
            return;
        }

        Login fb = FacebookConnect.getInstance();

        fb.setClientId(clientId);

        fb.setRedirectURI(redirectURI);

        fb.setClientSecret(clientSecret);
        login = fb;
        fb.setCallback(new LoginListener(LoginListener.FACEBOOK));
        User user = new User(); 
           user.setEmail("aura.forgetpass@gmail.com");

        if (!fb.isUserLoggedIn()) {

            fb.doLogin();

        } else {
           

            //trigger the login if not already logged in
            fb.doLogout();
            Storage.getInstance().writeObject("token", "");

           
            HomeClient homeClient = new HomeClient(user.getEmail());
            homeClient.show();
            

        }
        this.username=user.getEmail();

    }

    public class LoginListener extends LoginCallback {

        public static final int FACEBOOK = 0;

        //  public static final int GOOGLE = 1;
        private int loginType;

        public LoginListener(int loginType) {

            this.loginType = loginType;
        }

        public void loginSuccessful() {
            System.out.println("heey");
            String clientId = "2822192241425724";
            String redirectURI = "http://localhost/";
            String clientSecret = "0eba35b54a918564e867efc3aee9d8e2";

            Login fb = FacebookConnect.getInstance();

            fb.setClientId(clientId);

            fb.setRedirectURI(redirectURI);

            fb.setClientSecret(clientSecret);
            login = fb;

            fb.setCallback(new LoginListener(LoginListener.FACEBOOK));
            User user = new User();
            String token = fb.getAccessToken().getToken();

            FaceBookAccess.setToken(token);
            user.setEmail("aura.forgetpass@gmail.com");
            
            try {

                user = setUser(token);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //checkUser();
            String result = ServiceUser.getInstance().addUser(user);
            HomeClient homeClient;
            try {
                homeClient = new HomeClient( user.getEmail());
                homeClient.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        public void loginFailed(String errorMessage) {
            Dialog.show("Login Failed", errorMessage, "Ok", null);

        }
    }

    public User setUser(String token) throws IOException {
        ConnectionRequest req = new ConnectionRequest();
        req.setPost(false);
        req.setUrl("https://graph.facebook.com/v2.3/me");
        req.addArgumentNoEncoding("access_token", token);
        InfiniteProgress ip = new InfiniteProgress();
        Dialog d = ip.showInifiniteBlocking();
        NetworkManager.getInstance().addToQueueAndWait(req);
        byte[] data = req.getResponseData();
        JSONParser parser = new JSONParser();
        Map map = null;
        try {
            map = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String id = (String) map.get("id");
        String prenom = "Aura";
        String nom = "Pi";
        String password = "123456aA";
        String picture = "temp3230432898948394292s4..jpg";
        String tel = "";
        String email = "aura.forgetpass@gmail.com";
        User user = new User(id, nom, prenom, email, password, tel, picture);

        System.out.println(id);

        d.dispose();

        FaceBookAccess.setToken(token);
        return user;

        //final User me = new User();
//       
    }

}
