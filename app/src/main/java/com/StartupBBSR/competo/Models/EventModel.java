package com.StartupBBSR.competo.Models;

import java.io.Serializable;
import java.util.List;

public class EventModel  implements Serializable {
    private String eventPoster;
    private String eventTitle, eventDescription, eventVenue;
    private String eventDate, eventTime;
    private String eventLink;
    private List<String> eventTags;

    private String eventStatus;

    private String eventOrganizerID;
    private String eventID;

    private boolean expanded;

    public EventModel() {
//        Firebase needs empty constructor
    }

    public EventModel(String eventPoster, String eventTitle, String eventDescription, String eventVenue, String eventDate, String eventTime, String eventLink,List<String> eventTags, String eventOrganizerID, String eventID, String eventStatus) {
        this.eventPoster = eventPoster;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventVenue = eventVenue;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventLink = eventLink;
        this.eventTags = eventTags;
        this.eventOrganizerID = eventOrganizerID;
        this.eventID = eventID;
        this.eventStatus = eventStatus;

//        Only for expanding rv
        this.expanded = false;
    }

    public String getEventPoster() {
        return eventPoster;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getEventLink() {
        return eventLink;
    }

    public List<String> getEventTags() {
        return eventTags;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getEventOrganizerID() {
        return eventOrganizerID;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventStatus() {
        return eventStatus;
    }
}