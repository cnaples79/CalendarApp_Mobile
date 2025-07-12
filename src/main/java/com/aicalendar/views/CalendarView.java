package com.aicalendar.views;

import com.aicalendar.CalendarService;
import com.aicalendar.Event;
import com.gluonhq.charm.glisten.application.AppManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.CharmListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.time.format.DateTimeFormatter;

import java.io.IOException;

public class CalendarView extends AppViewBase {

    private static final Logger LOG = Logger.getLogger(CalendarView.class.getName());

    @FXML
    private CharmListView<Event, Comparable<?>> eventListView;

    public CalendarView(AppManager appManager, CalendarService calendarService) {
        super(appManager, calendarService);
        LOG.info("Constructing CalendarView");

        setOnShowing(e -> {
            LOG.info("CalendarView is showing");
            if (getCenter() == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(CalendarView.class.getResource("calendar.fxml"));
                    loader.setController(this);
                    setCenter(loader.load());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            setupBottomNavigation();
            initialize();
        });
    }

    private void initialize() {
        // Set a placeholder for when the list is empty or loading
        ProgressIndicator progressIndicator = new ProgressIndicator();
        eventListView.setPlaceholder(progressIndicator);

        // Use a service to load data in the background
        Service<ObservableList<Event>> service = new Service<>() {
            @Override
            protected Task<ObservableList<Event>> createTask() {
                return new Task<>() {
                    @Override
                    protected ObservableList<Event> call() throws Exception {
                        // This is a background thread
                        return FXCollections.observableArrayList(calendarService.getAllEvents());
                    }
                };
            }
        };

        service.setOnSucceeded(e -> {
            // This is the FX Application Thread
            eventListView.setItems(service.getValue());
        });

        service.setOnFailed(e -> {
            // Handle error, e.g., show an error message
            System.err.println("Failed to load events: " + service.getException());
            service.getException().printStackTrace();
            eventListView.setPlaceholder(new Label("Error loading events."));
        });

        service.start();
        eventListView.setCellFactory(p -> new EventCell());
    }

    private static class EventCell extends CharmListCell<Event> {
        private final VBox container;
        private final Label titleLabel;
        private final Label timeLabel;

        public EventCell() {
            titleLabel = new Label();
            titleLabel.setStyle("-fx-font-weight: bold;");
            timeLabel = new Label();
            container = new VBox(titleLabel, timeLabel);
            container.setSpacing(5);
        }

        @Override
        public void updateItem(Event item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                titleLabel.setText(item.getTitle());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                String time = item.getStartTime().format(formatter) + " - " + item.getEndTime().format(formatter);
                timeLabel.setText(time);
                setGraphic(container);
            }
        }
    }
}
