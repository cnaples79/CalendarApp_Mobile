package com.aicalendar.views;

import com.aicalendar.models.Event;
import com.aicalendar.services.CalendarService;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.ListTile;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

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
        List<Event> allEvents = calendarService.getAllEvents();
        timelineList.setItems(FXCollections.observableArrayList(allEvents));
            service.getException().printStackTrace();
            timelineListView.setPlaceholder(new Label("Error loading timeline."));
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
                timeLabel.setText(item.getStartTime().format(TIME_FORMATTER));
                titleLabel.setText(item.getTitle());
                setGraphic(container);
            }
        }
    }
}
