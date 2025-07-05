package com.aicalendar;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.NavigationButton;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.aicalendar.AppView.CALENDAR;

public class Main extends Application {

    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() {
        for (AppView view : AppView.values()) {
            view.register(appManager);
        }
    }

    @Override
    public void start(Stage stage) {
        appManager.start(stage);
    }

    private void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));

        // Add BottomNavigation
        BottomNavigation bottomNavigation = new BottomNavigation();
        for (AppView view : AppView.values()) {
            NavigationButton button = new NavigationButton(view.getTitle(), view.getIcon().graphic(), e -> appManager.switchView(view.name()));
            bottomNavigation.getActionItems().add(button);
        }
        appManager.getLayout().setBottom(bottomNavigation);

        // Start with the Calendar view
        appManager.switchView(CALENDAR.name());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
