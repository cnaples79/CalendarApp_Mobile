package com.aicalendar.views;

import com.aicalendar.AIService;
import com.aicalendar.CalendarService;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.CharmListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.logging.Logger;

public class ChatView extends AppViewBase {

    private static final Logger LOG = Logger.getLogger(ChatView.class.getName());

    @FXML
    private CharmListView<ChatMessage, Comparable<?>> messageListView;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button sendButton;

    private final ObservableList<ChatMessage> messages = FXCollections.observableArrayList();
        private final AIService aiService;

    public ChatView(AppManager appManager, CalendarService calendarService) {
        super(appManager, calendarService);
        LOG.info("Constructing ChatView");
        this.aiService = new AIService(calendarService);

        setOnShowing(e -> {
            LOG.info("ChatView is showing");
            if (getCenter() == null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aicalendar/views/chat.fxml"));
                    loader.setController(this);
                    setCenter(loader.load());
                    initialize();
                } catch (IOException ex) {
                    System.err.println("Error loading chat.fxml: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            setupBottomNavigation();
        });
    }

    private void initialize() {
        messageListView.setItems(messages);
        messageListView.setCellFactory(p -> new MessageCell());
        messages.add(new ChatMessage("Hello! How can I help you plan your day?", false));
    }

    @FXML
    private void sendMessage() {
        String text = messageTextField.getText();
        if (text == null || text.trim().isEmpty()) {
            return;
        }

        messages.add(new ChatMessage(text, true));
        messageTextField.clear();
        sendButton.setDisable(true);

        Service<String> service = new Service<>() {
            @Override
            protected Task<String> createTask() {
                return new Task<>() {
                    @Override
                    protected String call() throws Exception {
                        return aiService.getAIResponse(text).getTextResponse();
                    }
                };
            }
        };

        service.setOnSucceeded(e -> {
            messages.add(new ChatMessage(service.getValue(), false));
            messageListView.scrollTo(messages.size() - 1);
            sendButton.setDisable(false);
        });

        service.setOnFailed(e -> {
            messages.add(new ChatMessage("Sorry, something went wrong. Please try again.", false));
            System.err.println("Failed to get AI response: " + service.getException());
            service.getException().printStackTrace();
            sendButton.setDisable(false);
        });

        service.start();
    }

    private static class ChatMessage {
        private final String text;
        private final boolean isUserMessage;

        public ChatMessage(String text, boolean isUserMessage) {
            this.text = text;
            this.isUserMessage = isUserMessage;
        }

        public String getText() {
            return text;
        }

        public boolean isUserMessage() {
            return isUserMessage;
        }
    }

    private static class MessageCell extends CharmListCell<ChatMessage> {
        private final HBox container;
        private final Label label;

        public MessageCell() {
            label = new Label();
            label.setWrapText(true);
            label.setStyle("-fx-padding: 10; -fx-background-radius: 10;");
            container = new HBox(label);
        }

        @Override
        public void updateItem(ChatMessage item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                label.setText(item.getText());
                if (item.isUserMessage()) {
                    container.setAlignment(Pos.CENTER_RIGHT);
                    label.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 10;");
                } else {
                    container.setAlignment(Pos.CENTER_LEFT);
                    label.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 10; -fx-background-radius: 10;");
                }
                setGraphic(container);
            }
        }
    }
}
