package com.aicalendar.views;

import com.aicalendar.CalendarService;
import com.gluonhq.charm.glisten.application.AppManager;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ChatView extends AppViewBase {

        public ChatView(AppManager appManager, CalendarService calendarService) {
        super(appManager, calendarService);

        setOnShowing(e -> {
            if (getCenter() == null) {
                try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aicalendar/views/chat.fxml"));
                    setCenter(loader.load());
                    setupBottomNavigation();
                } catch (IOException ex) {
                                        System.err.println("Error loading chat.fxml: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }
}
