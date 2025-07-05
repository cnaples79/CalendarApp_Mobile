package com.aicalendar;

import com.aicalendar.views.CalendarView;
import com.aicalendar.views.ChatView;
import com.aicalendar.views.TimelineView;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

public class Main extends Application {

    public static final String CALENDAR_VIEW = HOME_VIEW;
    public static final String CHAT_VIEW = "Chat";
    public static final String TIMELINE_VIEW = "Timeline";

    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() {
        appManager.addViewFactory(CALENDAR_VIEW, () -> new CalendarView());
        appManager.addViewFactory(CHAT_VIEW, () -> new ChatView());
        appManager.addViewFactory(TIMELINE_VIEW, () -> new TimelineView());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        appManager.start(primaryStage);
    }

    private void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
