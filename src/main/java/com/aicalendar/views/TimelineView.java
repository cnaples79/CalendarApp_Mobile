package com.aicalendar.views;

import com.aicalendar.CalendarService;
import com.aicalendar.Event;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.CharmListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimelineView extends AppViewBase {

    private static final Logger LOG = Logger.getLogger(TimelineView.class.getName());

    @FXML
    private CharmListView<Event, Comparable<?>> timelineListView;

    public TimelineView(AppManager appManager, CalendarService calendarService) {
        super(appManager, calendarService);
        LOG.info("Constructing TimelineView");

        setOnShowing(e -> {
            LOG.info("TimelineView is showing");
            if (getCenter() == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aicalendar/views/timeline.fxml"));
                    loader.setController(this);
                    setCenter(loader.load());
                    initialize();
                } catch (IOException ex) {
                    System.err.println("Error loading timeline.fxml: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            setupBottomNavigation();
        });
    }

    private void initialize() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        timelineListView.setPlaceholder(progressIndicator);

        Service<ObservableList<Event>> service = new Service<>() {
            @Override
            protected Task<ObservableList<Event>> createTask() {
                return new Task<>() {
                    @Override
                    protected ObservableList<Event> call() throws Exception {
                        LocalDate today = LocalDate.now();
                        LocalDateTime startOfDay = today.atStartOfDay();
                        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusNanos(1);
                        return FXCollections.observableArrayList(calendarService.getEvents(startOfDay, endOfDay));
                    }
                };
            }
        };

        service.setOnSucceeded(e -> {
            timelineListView.setItems(service.getValue());
        });

        service.setOnFailed(e -> {
            System.err.println("Failed to load timeline events: " + service.getException());
            service.getException().printStackTrace();
            timelineListView.setPlaceholder(new Label("Error loading timeline."));
        });

        service.start();
        timelineListView.setCellFactory(p -> new TimelineEventCell());
    }

    private static class TimelineEventCell extends CharmListCell<Event> {
        private final VBox container = new VBox();
        private final Label timeLabel = new Label();
        private final Label titleLabel = new Label();
        private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

        public TimelineEventCell() {
            timeLabel.setFont(Font.font(14));
            titleLabel.setFont(Font.font(16));
            container.getChildren().addAll(timeLabel, titleLabel);
            container.setPadding(new Insets(10));
        }

        @Override
        public void updateItem(Event item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                timeLabel.setText(item.getStartTime().format(TIME_FORMATTER));
                titleLabel.setText(item.getTitle());
                setGraphic(container);
            }
        }
    }
}
