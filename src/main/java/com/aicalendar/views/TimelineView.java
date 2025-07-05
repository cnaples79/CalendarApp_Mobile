package com.aicalendar.views;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.control.AppBar;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class TimelineView extends View {

    public TimelineView() {
        setCenter(new Label("Timeline View"));
        getStyleClass().add("timeline-view");
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Timeline");
    }
}
