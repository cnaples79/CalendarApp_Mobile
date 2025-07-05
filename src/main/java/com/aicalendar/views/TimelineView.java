package com.aicalendar.views;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class TimelineView extends AppViewBase {

    public TimelineView() {
        setOnShowing(e -> {
            if (getCenter() == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aicalendar/timeline.fxml"));
                    setCenter(loader.load());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setupBottomNavigation();
    }
}
