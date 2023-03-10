package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Event;
import com.tfs.demo.tfs_crud_demo.service.EventService;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class EventRestController {

    private EventService eventService;
    private FoodService foodService;

    @Autowired
    public EventRestController(EventService theEventService,FoodService theFoodService){
        eventService = theEventService;
        foodService = theFoodService;
    }

    @GetMapping("/events")
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/events/{eventId}")
    public Event getEventById(@PathVariable int eventId){
        Event theEvent = eventService.getEventById(eventId);
        if(theEvent==null){
            throw new RuntimeException("Event with id - "+eventId+" not found!");
        }
        return theEvent;
    }

    @GetMapping("/events/name/{eventName}")
    public Event getEventByName(@PathVariable String eventName){
        Event event = eventService.getEventByName(eventName);
        if(event == null){
            throw new RuntimeException("Event with name - " +eventName + " not found!");
        }
        return event;
    }

    @PostMapping("/events")
    public String addNewEvent(@RequestBody Event theEvent){
        eventService.saveEvent(theEvent);
        return "Saved " +theEvent;
    }

    @PutMapping("/events")
    public Event updateEvent(@RequestBody Event theEvent){
        Event event = eventService.getEventById(theEvent.getEventId());
        if(theEvent.getEventName()==null){
            theEvent.setEventName(event.getEventName());
        }
        if(theEvent.getDescription()==null){
            theEvent.setDescription(event.getDescription());
        }
        if(theEvent.getImage_url()==null){
            theEvent.setImage_url(event.getImage_url());
        }
        if(theEvent.getFromDate()==null){
            theEvent.setFromDate(event.getFromDate());
        }
        if(theEvent.getToDate()==null){
            theEvent.setToDate(event.getToDate());
        }
        if(theEvent.getFoodList()==null){
            theEvent.setFoodList(event.getFoodList());
        }
        if(theEvent.getStatus()==null){
            theEvent.setStatus(event.getStatus());
        }
        theEvent.setFoodList(theEvent.getFoodList());
        eventService.saveEvent(theEvent);
        return theEvent;
    }

    @DeleteMapping("/events/{eventId}")
    public String disableEvent(@PathVariable int eventId){
        eventService.disableEvent(eventId);
        return "Disable event with id - " +eventId + " successful!";
    }

//    @PostMapping("/events/{foodId}TO{eventId}")
//    public String addFoodToEvent(@PathVariable String eventId,@PathVariable int foodId){
//        Food theFood = foodService.getFoodById(foodId);
//        Event theEvent = eventService.getEventById(eventId);
//
//        if(theEvent==null || theFood==null){
//            throw new RuntimeException("Event with id " +eventId+ " or food with id -"+foodId+ " not found!");
//        }
//        theEvent.addFood(theFood);
//        theFood.addEvent(theEvent);
//
//        foodService.saveFood(theFood);
//        eventService.saveEvent(theEvent);
//
//        return "Add food: " +theFood.getFoodName() + " to event: "+theEvent.getEventName()+" successful!";
//    }

}
