/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.objectif;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import aura.entities.Objectif;
import aura.services.ServiceObjectif;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Chirine
 */
public class ShowObjectifForm extends Form {

    public ShowObjectifForm(Form previous) {
        setTitle("Liste des Objectifs");
        Toolbar tb = new Toolbar(true);
        TextField btnRech = new TextField("Recherche par duree");
        add(btnRech);
        List<Objectif> list = ServiceObjectif.getInstance().getAllObjectifs();
        for (Objectif e : list) {
            if (e.getDuree() ==  Float.parseFloat(btnRech.getText())) {
                Container cnt1 = new Container(BoxLayout.y());
                Label lbnom = new Label("Description :" + e.getDescription());
                Button btn = new Button("Réponse :" + e.getReponse());
                cnt1.add(lbnom);
                cnt1.add(btn);

                Container cnt2 = new Container(BoxLayout.x());

                cnt2.add(cnt1);
                Form F2 = new Form("Détails", BoxLayout.y());
                Label lbi = new Label();
                Label lbn = new Label();
                Label lbem = new Label();
                Label lbag = new Label();
                Label lbnum = new Label();

                F2.addAll(lbi, lbn, lbem, lbag, lbnum);

                btn.addActionListener((p) -> {
                    lbn.setText("Description :" + e.getDescription());
                    lbem.setText("Réponse :" + e.getReponse());
                    lbnum.setText("Duree :" + e.getDuree());
                    lbag.setText("Date de debut :" + e.getDate());
                    System.out.println(e);
                    F2.show();

                });

                F2.getToolbar().addCommandToLeftBar("back", null, ev -> {
                    previous.show();
                });
                cnt2.setLeadComponent(btn);
                add(cnt2);

            }
        }

    }
}
