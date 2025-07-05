package com.aicalendar.views;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class TimelineView extends AppViewBase {

    public TimelineView() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("timeline.fxml"));
            setCenter(loader.load());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load timeline.fxml", e);
        }
        setupBottomNavigation();
    }
}
