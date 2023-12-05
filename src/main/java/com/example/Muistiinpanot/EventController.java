package com.example.Muistiinpanot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/events")
    public String list(Model model) {
        model.addAttribute("events", eventRepository.findAll());
        return "events";
    }

    @PostMapping("/events/create")
    public String create(@RequestParam String eventTitle, String eventDescription, String eventDate) {
        eventRepository.save(new Event(eventTitle, eventDescription, eventDate));
        return "redirect:/events";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/events";
    }
}