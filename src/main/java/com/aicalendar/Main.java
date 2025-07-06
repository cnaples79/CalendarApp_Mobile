package com.aicalendar;

import com.aicalendar.views.CalendarView;
import com.aicalendar.views.ChatView;
import com.aicalendar.views.TimelineView;
import com.aicalendar.CalendarService;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends MobileApplication {

        public static final String CALENDAR_VIEW = MobileApplication.HOME_VIEW;
    public static final String CHAT_VIEW = "Chat View";
        public static final String TIMELINE_VIEW = "Timeline View";

    private final CalendarService calendarService = new CalendarService();

    

    @Override
    public void init() {
                        AppManager appManager = AppManager.getInstance();
        appManager.addViewFactory(CALENDAR_VIEW, () -> new CalendarView(appManager, calendarService));
        appManager.addViewFactory(CHAT_VIEW, () -> new ChatView(appManager, calendarService));
        appManager.addViewFactory(TIMELINE_VIEW, () -> new TimelineView(appManager, calendarService));
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
