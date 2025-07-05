package com.aicalendar.views;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class CalendarView extends AppViewBase {

    public CalendarView() {
        setOnShowing(e -> {
            if (getCenter() == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aicalendar/views/calendar.fxml"));
                    setCenter(loader.load());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setupBottomNavigation();
    }
}
