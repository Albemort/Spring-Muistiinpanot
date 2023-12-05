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

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/events")
    public String list(Model model) {
        model.addAttribute("events", eventRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "events";
    }

    @PostMapping("/events/create")
    public String create(@RequestParam String eventTitle,
                         @RequestParam String eventDescription,
                         @RequestParam String eventDate,
                         @RequestParam String category) {

        Category existingCategory = categoryRepository.findByCategoryName(category)
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setCategoryName(category);
                    return categoryRepository.save(newCategory);
                });

        // Create a new Event
        Event newEvent = new Event();
        newEvent.setEventTitle(eventTitle);
        newEvent.setEventDescription(eventDescription);
        newEvent.setEventDate(eventDate);

        // Associate the Event with the Category
        newEvent.getCategories().add(existingCategory);

        // Save the Event
        eventRepository.save(newEvent);

        return "redirect:/events";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/events";
    }
}