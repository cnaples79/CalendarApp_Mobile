package com.aicalendar;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.Image;

import static com.aicalendar.AppView.CALENDAR;

public class Main extends MobileApplication {

    @Override
    public void init() {
        BottomNavigation bottomNavigation = createBottomNavigation();
        for (AppView view : AppView.values()) {
            view.register(this, bottomNavigation);
        }
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));
    }

    private BottomNavigation createBottomNavigation() {
        BottomNavigation bottomNavigation = new BottomNavigation();
        for (AppView view : AppView.values()) {
            BottomNavigationButton button = new BottomNavigationButton(view.getTitle(), view.getIcon().graphic(), e -> switchView(view.name()));
            bottomNavigation.getActionItems().add(button);
        }
        return bottomNavigation;
    }
}
