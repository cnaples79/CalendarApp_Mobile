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
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimelineView extends AppViewBase {

    private static final Logger LOG = Logger.getLogger(TimelineView.class.getName());

    @FXML
    private CharmListView<Event, LocalDate> timelineList;

    public TimelineView(AppManager appManager, CalendarService calendarService) {
        super("TimelineView", appManager, calendarService);
        LOG.info("Constructing TimelineView");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("timeline.fxml"));
            loader.setController(this);
            setCenter(loader.load());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error loading FXML for TimelineView", e);
            throw new RuntimeException(e);
        }

        setUseSpacer(true);

        setOnShowing(e -> {
            LOG.info("TimelineView is showing");
            loadEvents();
        });
    }

    @FXML
    private void initialize() {
        timelineList.setCellFactory(p -> new TimelineEventCell());
        timelineList.setComparator(Comparator.comparing(Event::getDateTime));
        timelineList.setHeadersFunction(event -> event.getDateTime().toLocalDate());
        timelineList.setHeaderCellFactory(p -> new DateHeaderCell());
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
                timeLabel.setText(item.getDateTime().toLocalTime().format(TIME_FORMATTER));
                titleLabel.setText(item.getTitle());
                setGraphic(container);
            }
        }
    }

    private static class DateHeaderCell extends CharmListCell<Event> {
        private final Label label = new Label();

        public DateHeaderCell() {
            getStyleClass().add("date-header");
            setGraphic(label);
            setText(null);
        }

        @Override
        public void updateItem(Event item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && getHeadersFunction() != null) {
                LocalDate headerDate = getHeadersFunction().apply(item);
                if (headerDate != null) {
                    label.setText(headerDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
                } else {
                    label.setText("");
                }
            } else {
                label.setText("");
            }
        }
    }
}
