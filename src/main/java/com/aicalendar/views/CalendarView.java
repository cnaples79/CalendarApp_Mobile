package com.aicalendar.views;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.control.AppBar;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class CalendarView extends View {

    public CalendarView() {
        setCenter(new Label("Calendar View"));
        getStyleClass().add("calendar-view");
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Calendar");
    }
}
