package com.aicalendar.views;

import com.gluonhq.charm.glisten.application.AppManager;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ChatView extends AppViewBase {

    public ChatView(AppManager appManager) {
        super(appManager);

        setOnShowing(e -> {
            if (getCenter() == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aicalendar/views/chat.fxml"));
                    setCenter(loader.load());
                } catch (IOException ex) {
                    System.err.println("Error loading chat.fxml: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        setupBottomNavigation();
    }
}
