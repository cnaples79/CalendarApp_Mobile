package com.aicalendar.views;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class TimelineView extends View {

    public TimelineView() {
        Label label = new Label("Timeline View");
        setCenter(label);
        getStyleClass().add("timeline-view");
    }

    @Override
    protected void updateAppBar(com.gluonhq.charm.glisten.control.AppBar appBar) {
        appBar.setTitleText("Timeline");
    }
}
