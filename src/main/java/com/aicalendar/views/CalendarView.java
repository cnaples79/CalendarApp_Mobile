package com.aicalendar.views;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class CalendarView extends View {
    public CalendarView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("calendar.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
