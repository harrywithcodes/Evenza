package com.evenza.event_service.controller;

import com.evenza.event_service.model.Event;
import com.evenza.event_service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> GetAllEvents() {
        return eventService.GetAllEvent();
    }
    @GetMapping("/{id}")
    public Event GetEventById(@PathVariable int id) {
        return eventService.GetEventById(id);
    }
    @PostMapping
    public Event CreateEvents(@RequestBody Event e) {
        return eventService.CreateEvent(e);
    }
    @DeleteMapping ("/{id}")
    public void DeleteEvents(@PathVariable int id) {
        eventService.DeleteEvent(id);
    }
    @GetMapping ("/search")
    public List<Event> searchevents (@RequestParam String title){
        return  eventService.SearchEvent(title);
    }

    @GetMapping ("/count")
    public long getEventCount(){
        return eventService.GetAllEvent().size();
    }
}

