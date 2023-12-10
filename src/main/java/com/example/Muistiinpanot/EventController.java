package com.example.Muistiinpanot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

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

    @GetMapping("/addevent")
    public String addevent(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "addevent";
    }

    @GetMapping("/events/{id}")
    public String getOneEvent(Model model, @PathVariable Long id) {
        model.addAttribute("events", eventRepository.getReferenceById(id));
        model.addAttribute("categories", categoryRepository.getReferenceById(id));
        return "events";
    }

    @DeleteMapping("/events/{id}")
    public String deleteEvent(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            eventRepository.deleteById(id);
            return "redirect:/events";
        } else {
            return "redirect:/events";
        }
    }

    @PostMapping("/events/create")
    public String createEvent(@RequestParam String eventTitle,
                         @RequestParam String eventDescription,
                         @RequestParam String eventDate,
                         @RequestParam String eventCategory) {

        try {
            Category existingCategory = categoryRepository.findByCategoryName(eventCategory)
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setCategoryName(eventCategory);
                        return categoryRepository.save(newCategory);
                    });

            // Create a new Event
            Event newEvent = new Event();
            newEvent.setEventTitle(eventTitle);
            newEvent.setEventDescription(eventDescription);

            // Parse the String eventDate to Java.time object ld
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM-dd");
                LocalDate ld = LocalDate.parse(eventDate, f);
                newEvent.setEventDate(ld);
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date: " + eventDate);
                e.printStackTrace();
                return "redirect:/events";
            }

            // Associate the Event with the Category
            newEvent.getCategories().add(existingCategory);

            // Save the Event
            eventRepository.save(newEvent);

        } catch (Exception e) {
            System.err.println("Exception error: " + e);
            e.printStackTrace();
        }
        return "redirect:/events";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/events";
    }
}