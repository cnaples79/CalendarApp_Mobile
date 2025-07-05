package com.aicalendar.views;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import static com.aicalendar.Main.CALENDAR_VIEW;
import static com.aicalendar.Main.CHAT_VIEW;
import static com.aicalendar.Main.TIMELINE_VIEW;

public abstract class AppViewBase extends View {

    public AppViewBase() {
        // The super constructor of View (which is a BorderPane) is called implicitly.
    }

    protected void setupBottomNavigation() {
        BottomNavigation bottomNavigation = new BottomNavigation();

        BottomNavigationButton calendarButton = new BottomNavigationButton("Calendar", MaterialDesignIcon.DATE_RANGE.graphic(), e -> AppManager.getInstance().switchView(CALENDAR_VIEW));
        BottomNavigationButton chatButton = new BottomNavigationButton("Chat", MaterialDesignIcon.CHAT.graphic(), e -> AppManager.getInstance().switchView(CHAT_VIEW));
        BottomNavigationButton timelineButton = new BottomNavigationButton("Timeline", MaterialDesignIcon.TIMELINE.graphic(), e -> AppManager.getInstance().switchView(TIMELINE_VIEW));

        bottomNavigation.getActionItems().addAll(calendarButton, chatButton, timelineButton);

        setBottom(bottomNavigation);
    }
}
