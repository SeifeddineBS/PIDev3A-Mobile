/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.gestionUser;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import aura.entities.User;
import aura.gui.HomeForm;

import aura.services.ServiceUser;
import aura.services.Validator;
import java.io.IOException;


/**
 *
 * @author SeifBS
 */
public class forgetPassword extends Form {
    Form current;
    public String username;
    public User user;
    
    public forgetPassword()
    {      
    }
    
    public forgetPassword(Form previous) {
        this.username=forgetPasswordCheck.username;

         current = this; //Récupération de l'interface(Form) en cours
        setTitle("Modifer vos parametres");
        Validator v=new Validator();


        
        
        TextField new_password= new TextField("", "Nouveau mot de passe");
        TextField confirmer_password= new TextField("", "Confirmer mot de passe");
        Button btnModifier = new Button("Modifier votre mot de passe");
        
             new_password.setConstraint(TextField.PASSWORD);
        confirmer_password.setConstraint(TextField.PASSWORD);
        
                btnModifier.addActionListener((e) -> {
                    
                   
               
                   if (!v.test_Password(new_password.getText()))
                    {
                    Dialog.show("Attention", "Votre passe doit contenir majuscule un chiffre et une miniscule au minimum", new Command("OK"));
                    }
                  
                  else if (!new_password.getText().equals(confirmer_password.getText()))
                {
                Dialog.show("Attention", "Les deux mot de passes ne correspondent pas", new Command("OK"));
                }
                else
                {
                    
                        //User u = new User(Integer.parseInt(tfStatus.getText()), tfName.getText());
                        User u = new User(this.username,new_password.getText());

                        if( ServiceUser.getInstance().modifierUserMotDePasse(u))
                        {
                            Dialog.show("Success","Modification effectuée",new Command("OK"));
                            try {
                                new HomeForm().show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    }              
                });


        

        
                addAll(new_password,confirmer_password,btnModifier);
        
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());

    }

}
