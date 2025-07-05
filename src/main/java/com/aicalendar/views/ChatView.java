package com.aicalendar.views;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ChatView extends AppViewBase {

    public ChatView() {
        setOnShowing(e -> {
            if (getCenter() == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aicalendar/views/chat.fxml"));
                    setCenter(loader.load());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setupBottomNavigation();
    }
}
