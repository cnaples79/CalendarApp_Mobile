package com.aicalendar.views;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class TimelineView extends View {
    public TimelineView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("timeline.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
