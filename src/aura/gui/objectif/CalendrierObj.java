/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aura.gui.objectif;

import aura.entities.User;
import static aura.gui.objectif.CalendarForm.user;
import aura.services.ServiceUser;
import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import java.util.ArrayList;

/**
 *
 * @author Chirine
 */
public class CalendrierObj extends Container {

    public static User user;
    public static String username;

    int length = 37;
    private ComboBox year;
    private static final String[] DAYS = {"Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
    private static final String[] LABELS = {"Di", "Lu", "Ma", "Me", "Je", "Ve", "Sa"};

    private ArrayList<Button> allButtons = new ArrayList<Button>();
    //Button newButton = new Button("");

    public CalendrierObj(Form current, String username) {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.user = ServiceUser.getInstance().getUser(username);
        this.username = user.getId();
        Container calendarTitle = new Container(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        Container title = new Container(new GridLayout(1, 7));
        Container days = new Container(new GridLayout(6, 7));
        Container calendarTitleCopy = new Container(new GridLayout(1, 1));
        calendarTitleCopy.setUIID("CalendarTitleCopy");
        this.addComponent(calendarTitleCopy);
        this.addComponent(title);
        this.addComponent(days);

        Button prevMonth = new Button("<");
        Button nextMonth = new Button(">");
        SpanLabel monthYear = new SpanLabel("Mois " + " Année");
        calendarTitle.add(BorderLayout.WEST, prevMonth);
        calendarTitle.add(BorderLayout.CENTER, monthYear);
        calendarTitle.add(BorderLayout.EAST, nextMonth);
        calendarTitleCopy.add(calendarTitle);
        Button dayButton = new Button();

        if (UIManager.getInstance().isThemeConstant("calTitleDayStyleBool", false)) {
            title.setUIID("CalendarTitleArea");
            days.setUIID("CalendarDayArea");
        }
        for (int iter = 0; iter < DAYS.length; iter++) {
            title.addComponent(createDayTitle(iter));
        }
        for (int iter = 1; iter < length; iter++) {
            dayButton = new Button("" + iter);
            String date = "2021-05-" + iter;
            dayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Log.p("Action event triggered");
                    Picker datePicker = new Picker();
                    datePicker.setType(Display.PICKER_TYPE_DATE);
                    new AddObjectifForm(current, date, username).show();
                    //Button b1 = (Button)(evt.getActualComponent());
                    //Log.p( b1.getText() );
                }
            });

            allButtons.add(dayButton);
            days.addComponent(dayButton);
            if (iter <= 7) {
                dayButton.setNextFocusUp(year);
            }
        }
    }

    protected Label createDayTitle(int day) {
        String value = getUIManager().localize("Calendar." + DAYS[day], LABELS[day]);
        Label dayh = new Label(value, "Label");
        dayh.setEndsWith3Points(false);
        dayh.setTickerEnabled(false);
        return dayh;
    }
}
