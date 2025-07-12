package com.aicalendar.views;

import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.aicalendar.CalendarService;
import com.aicalendar.services.CalendarService;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.mvc.View;

import java.util.logging.Logger;

public abstract class AppViewBase extends View {

    private static final Logger LOG = Logger.getLogger(AppViewBase.class.getName());

    protected final AppManager appManager;
    protected final CalendarService calendarService;

    public AppViewBase(String viewName, AppManager appManager, CalendarService calendarService) {
        super(viewName);
        LOG.info("Constructing AppViewBase for: " + viewName);
        this.appManager = appManager;
        this.calendarService = calendarService;
    }

    protected void setupBottomNavigation() {
        BottomNavigation bottomNavigation = new BottomNavigation();

        BottomNavigationButton calendarButton = new BottomNavigationButton("Calendar", MaterialDesignIcon.DATE_RANGE.graphic(), e -> appManager.switchView(CALENDAR_VIEW));
        BottomNavigationButton chatButton = new BottomNavigationButton("Chat", MaterialDesignIcon.CHAT.graphic(), e -> appManager.switchView(CHAT_VIEW));
        BottomNavigationButton timelineButton = new BottomNavigationButton("Timeline", MaterialDesignIcon.TIMELINE.graphic(), e -> appManager.switchView(TIMELINE_VIEW));

        bottomNavigation.getActionItems().addAll(calendarButton, chatButton, timelineButton);

        setBottom(bottomNavigation);
    }
}
