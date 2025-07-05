package com.aicalendar.views;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ChatView extends AppViewBase {

    public ChatView() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
            setCenter(loader.load());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load chat.fxml", e);
        }
        setupBottomNavigation();
    }
}
