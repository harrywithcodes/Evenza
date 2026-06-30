package com.evenza.event_service.service;

import com.evenza.event_service.model.Event;
import com.evenza.event_service.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> GetAllEvent() {
        return eventRepository.findAll();
    }
    public Event GetEventById(int id) {
        return eventRepository.findById(id).orElse(null);       
    }

    public Event CreateEvent(Event e) {
        return eventRepository.save(e);
    }

    public void DeleteEvent(int id) {
        eventRepository.deleteById(id);
    }
    public List<Event> SearchEvent (String title){
        return eventRepository.findByTitleContainingIgnoreCase(title);
    }
}