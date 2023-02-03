package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Event;
import com.tfs.demo.tfs_crud_demo.entity.Food;
import com.tfs.demo.tfs_crud_demo.service.EventService;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("Return list of all events")
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/events/{eventId}")
    @ApiOperation("Return event based on eventId")
    public Event getEventById(@PathVariable String eventId){
        Event theEvent = eventService.getEventById(eventId);
        if(theEvent==null){
            throw new RuntimeException("Event with id - "+eventId+" not found!");
        }
        return theEvent;
    }

    @PostMapping("/events")
    @ApiOperation("Add new event(need full Event's JSON)")
    public String addNewEvent(@RequestBody Event theEvent){
        if(!eventService.CheckDuplicateEventId(theEvent.getEventId())){
            return "Duplicate issue with event id has been found!";
        }
        eventService.saveEvent(theEvent);
        return "Saved " +theEvent;
    }

    @PutMapping("/events")
    @ApiOperation("Update existing event (need full Event's JSON)")
    public Event updateEvent(@RequestBody Event theEvent){
        eventService.saveEvent(theEvent);
        return theEvent;
    }

    @DeleteMapping("/events/{eventId}")
    @ApiOperation("Disable event based on eventId")
    public String disableEvent(@PathVariable String eventId){
        eventService.disableEvent(eventId);
        return "Disable event with id - " +eventId + " successful!";
    }

    @PostMapping("/events/{foodId}TO{eventId}")
    @ApiOperation("Add food to event (need 2 pathvariable: foodId and eventId)")
    public String addFoodToEvent(@PathVariable String eventId,@PathVariable int foodId){
        Food theFood = foodService.getFoodById(foodId);
        Event theEvent = eventService.getEventById(eventId);

        if(theEvent==null || theFood==null){
            throw new RuntimeException("Event with id " +eventId+ " or food with id -"+foodId+ " not found!");
        }
        theEvent.addFood(theFood);
        theFood.addEvent(theEvent);

        foodService.saveFood(theFood);
        eventService.saveEvent(theEvent);

        return "Add food: " +theFood.getFoodName() + " to event: "+theEvent.getEventName()+" successful!";
    }

}
