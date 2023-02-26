package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.Event;

import java.util.List;

public interface EventService {

    public List<Event> getAllEvents();

    public Event getEventById(String eventId);

    public Event getEventByName(String eventName);

    public void saveEvent(Event theEvent);

    public void disableEvent(String eventId);

    public boolean CheckDuplicateEventId(String eventId);
}
