package com.aicalendar.views;

import com.aicalendar.CalendarService;
import com.aicalendar.Event;
import com.gluonhq.charm.glisten.application.AppManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

import java.io.IOException;

public class CalendarView extends AppViewBase {

    @FXML
    private ListView<Event> eventListView;

    public CalendarView(AppManager appManager, CalendarService calendarService) {
        super(appManager, calendarService);

        setOnShowing(e -> {
            if (getCenter() == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(CalendarView.class.getResource("calendar.fxml"));
                    loader.setController(this);
                    setCenter(loader.load());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            setupBottomNavigation();
            eventListView.setItems(FXCollections.observableArrayList(calendarService.getAllEvents()));
        });
    }
}
