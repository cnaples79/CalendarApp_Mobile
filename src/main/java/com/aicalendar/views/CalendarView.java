package com.aicalendar.views;

import com.gluonhq.charm.glisten.application.AppManager;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class CalendarView extends AppViewBase {

    public CalendarView(AppManager appManager) {
        super(appManager);

        setOnShowing(e -> {
            if (getCenter() == null) {
                try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aicalendar/views/calendar.fxml"));
                    setCenter(loader.load());
                    setupBottomNavigation();
                } catch (IOException ex) {
                                        System.err.println("Error loading calendar.fxml: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }
}
