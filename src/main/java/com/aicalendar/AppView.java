package com.aicalendar;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.aicalendar.views.CalendarView;
import com.aicalendar.views.ChatView;
import com.aicalendar.views.TimelineView;

import java.util.function.Supplier;

public enum AppView {

    CALENDAR("Calendar", CalendarView::new, MaterialDesignIcon.DATE_RANGE),
    CHAT("Chat", ChatView::new, MaterialDesignIcon.CHAT),
    TIMELINE("Timeline", TimelineView::new, MaterialDesignIcon.TIMELINE);

    private final String title;
    private final Supplier<com.gluonhq.charm.glisten.mvc.View> viewSupplier;
    private final MaterialDesignIcon icon;

    AppView(String title, Supplier<com.gluonhq.charm.glisten.mvc.View> viewSupplier, MaterialDesignIcon icon) {
        this.title = title;
        this.viewSupplier = viewSupplier;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public Supplier<com.gluonhq.charm.glisten.mvc.View> getViewSupplier() {
        return viewSupplier;
    }

    public MaterialDesignIcon getIcon() {
        return icon;
    }

    public void register(AppManager appManager) {
        appManager.addViewFactory(name(), viewSupplier);
    }
}
