package com.aicalendar.views;

import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.control.AppBar;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class ChatView extends View {

    public ChatView() {
        setCenter(new Label("Chat View"));
        getStyleClass().add("chat-view");
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Chat");
    }
}
