/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.objectif;

import aura.entities.User;
import static aura.gui.objectif.CalendarForm.user;
import aura.services.ServiceUser;
import com.codename1.capture.Capture;
import com.codename1.components.MultiButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Chirine
 */
public class CaptureObjectif extends Form {

    public static User user;
    public static String username;

    CaptureObjectif(String username) {
        this.user = ServiceUser.getInstance().getUser(username);
        this.username = user.getId();
        setToolbar(
                new Toolbar());
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_MIC, s);

        FileSystemStorage fs = FileSystemStorage.getInstance();
        String recordingsDir = fs.getAppHomePath() + "recordings/";

        fs.mkdir(recordingsDir);

        try {
            for (String file : fs.listFiles(recordingsDir)) {
                MultiButton mb = new MultiButton(file.substring(file.lastIndexOf("/") + 1));
                mb.addActionListener((e) -> {
                    try {
                        Media m = MediaManager.createMedia(recordingsDir + file, false);
                        m.play();
                    } catch (IOException err) {
                        Log.e(err);
                    }
                });
                add(mb);
            }

            getToolbar().addCommandToRightBar("", icon, (ev) -> {
                try {
                    String file = Capture.captureAudio();
                    if (file != null) {
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MMM-dd-kk-mm");
                        String fileName = sd.format(new Date());
                        String filePath = recordingsDir + fileName;
                        Util.copy(fs.openInputStream(file), fs.openOutputStream(filePath));
                        MultiButton mb = new MultiButton(fileName);
                        mb.addActionListener((e) -> {
                            try {
                                Media m = MediaManager.createMedia(filePath, false);
                                m.play();
                            } catch (IOException err) {
                                Log.e(err);
                            }
                        });
                        add(mb);
                        revalidate();
                    }
                } catch (IOException err) {
                    Log.e(err);
                }
            });
        } catch (IOException err) {
            Log.e(err);
        }
        Form formm = new ObjectifForm(username);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                 e -> formm.showBack());
        show();
    }

}
