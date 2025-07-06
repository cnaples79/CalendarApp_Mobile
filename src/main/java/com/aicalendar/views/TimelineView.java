package com.aicalendar.views;

import com.aicalendar.CalendarService;
import com.gluonhq.charm.glisten.application.AppManager;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class TimelineView extends AppViewBase {

        public TimelineView(AppManager appManager, CalendarService calendarService) {
        super(appManager, calendarService);

        setOnShowing(e -> {
            if (getCenter() == null) {
                try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aicalendar/views/timeline.fxml"));
                    setCenter(loader.load());
                    setupBottomNavigation();
                } catch (IOException ex) {
                                        System.err.println("Error loading timeline.fxml: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }
}
