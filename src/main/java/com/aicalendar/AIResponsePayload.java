package com.aicalendar;

public class AIResponsePayload {
    private String textResponse;
    private boolean eventCreated;
    private boolean eventModified;
    private Event event;

    public AIResponsePayload(String textResponse, boolean eventCreated, boolean eventModified, Event event) {
        this.textResponse = textResponse;
        this.eventCreated = eventCreated;
        this.eventModified = eventModified;
        this.event = event;
    }

    public AIResponsePayload(String textResponse, boolean eventCreated, boolean eventModified) {
        this(textResponse, eventCreated, eventModified, null);
    }

    public String getTextResponse() {
        return textResponse;
    }

    public boolean isEventCreated() {
        return eventCreated;
    }

    public boolean isEventModified() {
        return eventModified;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return "AIResponsePayload{" +
                "textResponse='" + textResponse + '\'' +
                ", eventCreated=" + eventCreated +
                ", eventModified=" + eventModified +
                ", event=" + event +
                '}';
    }
}
