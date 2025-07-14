package com.aicalendar.views;

import com.aicalendar.CalendarService;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;

import java.util.logging.Logger;

public abstract class AppViewBase extends View {

    private static final Logger LOG = Logger.getLogger(AppViewBase.class.getName());

    public static final String CALENDAR_VIEW = "Calendar View";
    public static final String CHAT_VIEW = "Chat View";
    public static final String TIMELINE_VIEW = "Timeline View";

    protected static AppManager appManager;
    protected final CalendarService calendarService;

    public AppViewBase(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public static void injectAppManager(AppManager manager) {
        appManager = manager;
    }

    protected void setupBottomNavigation() {
        BottomNavigation bottomNavigation = new BottomNavigation();

        BottomNavigationButton calendarButton = new BottomNavigationButton(CALENDAR_VIEW, MaterialDesignIcon.DATE_RANGE.graphic(), e -> appManager.switchView(CALENDAR_VIEW));
        BottomNavigationButton chatButton = new BottomNavigationButton(CHAT_VIEW, MaterialDesignIcon.CHAT.graphic(), e -> appManager.switchView(CHAT_VIEW));
        BottomNavigationButton timelineButton = new BottomNavigationButton(TIMELINE_VIEW, MaterialDesignIcon.TIMELINE.graphic(), e -> appManager.switchView(TIMELINE_VIEW));

        bottomNavigation.getActionItems().addAll(calendarButton, chatButton, timelineButton);

        setBottom(bottomNavigation);
    }
}
