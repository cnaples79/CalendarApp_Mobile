package com.aicalendar.views;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class CalendarView extends AppViewBase {

    public CalendarView() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("calendar.fxml"));
            setCenter(loader.load());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load calendar.fxml", e);
        }
        setupBottomNavigation();
    }
}
