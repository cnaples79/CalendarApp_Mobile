package com.aicalendar.views;

import com.aicalendar.abstracts.AppViewBase;
import com.aicalendar.models.Event;
import com.aicalendar.services.CalendarService;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.ListTile;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalendarView extends AppViewBase {

    private static final Logger LOG = Logger.getLogger(CalendarView.class.getName());

    @FXML
    private CharmListView<Event, LocalDate> eventList;

    @FXML
    private Label dateLabel;

    public CalendarView(AppManager appManager, CalendarService calendarService) {
        super("CalendarView", appManager, calendarService);
        LOG.info("Constructing CalendarView");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("calendar.fxml"));
            loader.setController(this);
            setCenter(loader.load());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error loading FXML for CalendarView", e);
            throw new RuntimeException(e);
        }

        setOnShowing(e -> {
            LOG.info("CalendarView is showing");
            if (dateLabel != null) {
                dateLabel.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
            }
            loadEvents();
        });
    }

    @FXML
    public void initialize() {
        eventList.setCellFactory(p -> new EventCell());
        eventList.setComparator(Comparator.comparing(Event::getDateTime));
        eventList.setHeadersFunction(event -> event.getDateTime().toLocalDate());
        eventList.setHeaderCellFactory(p -> new DateHeaderCell());
    }

    private void loadEvents() {
        List<Event> eventsForToday = calendarService.getEventsForDate(LocalDate.now());
        eventList.setItems(FXCollections.observableArrayList(eventsForToday));
    }

    private static class EventCell extends CharmListCell<Event> {
        private final ListTile tile = new ListTile();
        private final Label titleLabel = new Label();
        private final Label timeLabel = new Label();

        public EventCell() {
            tile.setPrimaryGraphic(MaterialDesignIcon.EVENT.graphic());
            tile.textProperty().setAll(titleLabel, timeLabel);
            setText(null);
        }

        @Override
        public void updateItem(Event item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
                titleLabel.setText(item.getTitle());
                timeLabel.setText(item.getDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("h:mm a")));
                setGraphic(tile);
            } else {
                setGraphic(null);
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
