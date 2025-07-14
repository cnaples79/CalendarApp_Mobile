package com.aicalendar;

import com.aicalendar.views.CalendarView;
import com.aicalendar.views.ChatView;
import com.aicalendar.views.TimelineView;
import com.aicalendar.views.AppViewBase;
import com.aicalendar.CalendarService;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static final String CALENDAR_VIEW = AppManager.HOME_VIEW;
    public static final String CHAT_VIEW = "Chat View";
    public static final String TIMELINE_VIEW = "Timeline View";

    private final CalendarService calendarService = new CalendarService();
    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() {
        LOG.info("Initializing Application");
        LOG.info("Creating Calendar View Factory");
        appManager.addViewFactory(CALENDAR_VIEW, () -> new CalendarView(calendarService));
        LOG.info("Creating Chat View Factory");
        appManager.addViewFactory(CHAT_VIEW, () -> new ChatView(calendarService));
        LOG.info("Creating Timeline View Factory");
        appManager.addViewFactory(TIMELINE_VIEW, () -> new TimelineView(calendarService));
    }

    @Override
    public void start(Stage stage) throws Exception {
        appManager.start(stage);
    }

    private void postInit(Scene scene) {
        AppViewBase.injectAppManager(appManager);
        LOG.info("Post-Initializing Application");
        Swatch.BLUE.assignTo(scene);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));
        ((Stage) scene.getWindow()).setTitle("AI Calendar");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
