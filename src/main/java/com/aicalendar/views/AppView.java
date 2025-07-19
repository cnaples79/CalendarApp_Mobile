package com.aicalendar.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

public class AppView {

    private final String name;

    public AppView(String name) {
        this.name = name;
    }

    public View getView() {
        View view = new View(name) {
            @Override
            protected void updateAppBar(AppBar appBar) {
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> System.out.println("Menu")));
                appBar.setTitleText("AI Calendar");
            }
        };
        return view;
    }
}
