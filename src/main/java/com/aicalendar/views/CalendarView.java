package com.aicalendar.views;

import com.gluonhq.charm.glisten.control.CharmListCell;
import com.aicalendar.views.AppViewBase;
import com.aicalendar.Event;
import com.aicalendar.CalendarService;
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
import java.time.LocalDateTime;
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
        super(AppViewBase.CALENDAR_VIEW, appManager, calendarService);
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
        eventList.setCellFactory(p -> new CharmListCell<Event>() {
            private final ListTile tile = new ListTile();

            @Override
            public void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    tile.textProperty().setAll(item.getTitle(), item.getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("h:mm a")));
                    setGraphic(tile);
                } else {
                    setGraphic(null);
                }
            }
        });

        eventList.setHeadersFunction(event -> event.getStartTime().toLocalDate());
        eventList.setHeaderCellFactory(p -> new CharmListCell<Event>() {
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
        LocalDate selectedDate = LocalDate.now();
        if (selectedDate != null) {
            LocalDateTime startOfDay = selectedDate.atStartOfDay();
            LocalDateTime endOfDay = selectedDate.atTime(23, 59, 59);
            eventList.setItems(FXCollections.observableArrayList(calendarService.getEvents(startOfDay, endOfDay)));
        }
    }
}
