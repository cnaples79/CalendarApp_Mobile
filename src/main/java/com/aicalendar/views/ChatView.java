package com.aicalendar.views;

import com.aicalendar.AIService;
import com.aicalendar.AIResponsePayload;
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
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatView extends AppViewBase {

    private static final Logger LOG = Logger.getLogger(ChatView.class.getName());

    @FXML
    private CharmListView<ChatMessage, String> chatArea;

    @FXML
    private TextField messageInput;

    @FXML
    private Button sendButton;

    private final ObservableList<ChatMessage> messages = FXCollections.observableArrayList();
    private final AIService aiService;

    public ChatView(AppManager appManager, CalendarService calendarService) {
        super(AppViewBase.CHAT_VIEW, appManager, calendarService);
        LOG.info("Constructing ChatView");
        this.aiService = new AIService(calendarService);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
            loader.setController(this);
            setCenter(loader.load());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error loading FXML for ChatView", e);
            throw new RuntimeException(e);
        }



        setOnShowing(e -> {
            LOG.info("ChatView is showing");
            chatArea.setItems(messages);
        });
    }

    @FXML
    private void initialize() {
        chatArea.setCellFactory(p -> new MessageCell());
        sendButton.setOnAction(e -> sendMessage());
        messageInput.setOnAction(e -> sendMessage());
    }

    private void sendMessage() {
        String text = messageInput.getText().trim();
        if (text.isEmpty()) {
            return;
        }

        messages.add(new ChatMessage(text, true));
        messageInput.clear();
        sendButton.setDisable(true);

        Service<AIResponsePayload> service = new Service<>() {
            @Override
            protected Task<AIResponsePayload> createTask() {
                return new Task<>() {
                    @Override
                    protected AIResponsePayload call() throws Exception {
                        return aiService.getAIResponse(text);
                    }
                };
            }
        };

        service.setOnSucceeded(e -> {
            AIResponsePayload aiResponse = (AIResponsePayload) e.getSource().getValue();
            messages.add(new ChatMessage(aiResponse.getTextResponse(), false));
            if (aiResponse.isEventCreated() || aiResponse.isEventModified()) {
                calendarService.eventsUpdatedProperty().set(!calendarService.eventsUpdatedProperty().get());
            }
            sendButton.setDisable(false);
        });

        service.setOnFailed(e -> {
            messages.add(new ChatMessage("Sorry, something went wrong. Please try again.", false));
            LOG.log(Level.SEVERE, "Failed to get AI response", service.getException());
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
