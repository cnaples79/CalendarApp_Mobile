package com.aicalendar.views;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class CalendarView extends View {

    public CalendarView() {
        Label label = new Label("Calendar View");
        setCenter(label);
        getStyleClass().add("calendar-view");
    }

    @Override
    protected void updateAppBar(com.gluonhq.charm.glisten.control.AppBar appBar) {
        appBar.setTitleText("Calendar");
    }
}
