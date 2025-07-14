package com.aicalendar.views;

import com.aicalendar.Event;
import com.aicalendar.CalendarService;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.ListTile;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimelineView extends AppViewBase {

    private static final Logger LOG = Logger.getLogger(TimelineView.class.getName());

    @FXML
    private CharmListView<Event, LocalDate> timelineList;

    public TimelineView(CalendarService calendarService) {
        super(calendarService);
        LOG.info("Constructing TimelineView");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("timeline.fxml"));
            loader.setController(this);
            setCenter(loader.load());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error loading FXML for TimelineView", e);
            throw new RuntimeException(e);
        }



        setOnShowing(e -> {
            LOG.info("TimelineView is showing");
            loadEvents();
        });
    }

    @FXML
    private void initialize() {
        timelineList.setComparator(Comparator.comparing(Event::getStartTime));
        timelineList.setHeadersFunction(event -> event.getStartTime().toLocalDate());

        timelineList.setCellFactory(p -> new CharmListCell<Event>() {
            private final ListTile tile = new ListTile();
            private final Label timeLabel = new Label();

            {
                tile.setPrimaryGraphic(timeLabel);
            }

            @Override
            public void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    timeLabel.setText(item.getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                    tile.textProperty().setAll(item.getTitle(), item.getDescription());
                    setGraphic(tile);
                } else {
                    setGraphic(null);
                }
            }
        });

        timelineList.setHeaderCellFactory(p -> new CharmListCell<Event>() {
            private final Label label = new Label();
            {
                getStyleClass().add("date-header");
            }

            @Override
            public void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    LocalDate headerDate = item.getStartTime().toLocalDate();
                    if (headerDate != null) {
                        label.setText(headerDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
                        setGraphic(label);
                    } else {
                        setGraphic(null);
                    }
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    private void loadEvents() {
        Service<List<Event>> service = new Service<>() {
            @Override
            protected Task<List<Event>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Event> call() throws Exception {
                        return calendarService.getAllEvents();
                    }
                };
            }
        };

        service.setOnSucceeded(e -> {
            timelineList.setItems(FXCollections.observableArrayList((List<Event>) e.getSource().getValue()));
        });

        service.setOnFailed(e -> {
            LOG.log(Level.SEVERE, "Failed to load timeline events", service.getException());
            timelineList.setPlaceholder(new Label("Error loading timeline."));
        });

        service.start();
    }
}
