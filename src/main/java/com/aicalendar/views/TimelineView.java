package com.aicalendar.views;

import com.gluonhq.charm.glisten.application.AppManager;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class TimelineView extends AppViewBase {

    public TimelineView(AppManager appManager) {
        super(appManager);

        setOnShowing(e -> {
            if (getCenter() == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aicalendar/timeline.fxml"));
                    setCenter(loader.load());
                } catch (IOException ex) {
                    System.err.println("Error loading timeline.fxml: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        setupBottomNavigation();
    }
}
