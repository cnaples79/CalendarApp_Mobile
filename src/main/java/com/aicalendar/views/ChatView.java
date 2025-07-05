package com.aicalendar.views;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class ChatView extends View {

    public ChatView() {
        Label label = new Label("Chat View");
        setCenter(label);
        getStyleClass().add("chat-view");
    }

    @Override
    protected void updateAppBar(com.gluonhq.charm.glisten.control.AppBar appBar) {
        appBar.setTitleText("Chat");
    }
}
