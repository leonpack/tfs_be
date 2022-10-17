package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.EventRepository;
import com.tfs.demo.tfs_crud_demo.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImplementation implements EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventServiceImplementation(EventRepository theEventRepository){
        eventRepository = theEventRepository;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(String eventId) {
        Optional<Event> result = eventRepository.findById(eventId);

        Event theEvent = null;
        if(result.isPresent()){
            theEvent = result.get();
        } else {
            throw new RuntimeException("Event with id - " +eventId + " not found!");
        }
        return theEvent;
    }

    @Override
    public void saveEvent(Event theEvent) {
        eventRepository.save(theEvent);
    }

    @Override
    public void disableEvent(String eventId) {
        Optional<Event> result = eventRepository.findById(eventId);
        Event theEvent = null;
        if(result.isPresent()){
            theEvent = result.get();
        }else {
            throw new RuntimeException("Event with id - " +eventId + " not found!");
        }
        theEvent.setStatus(false);
        eventRepository.save(theEvent);
    }

    @Override
    public boolean CheckDuplicateEventId(String eventId) {
        Optional<Event> result = eventRepository.findById(eventId);
        Event theEvent = null;
        if(result.isPresent()){
            throw new RuntimeException("Event with id - " +eventId +" already exist, please try again!");
        }

        return true;
    }
}
