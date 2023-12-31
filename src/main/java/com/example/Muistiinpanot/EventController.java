package com.example.Muistiinpanot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public EventController(EventRepository eventRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }


    // Get path to create events from the UI
    @GetMapping("/events/create")
    public String createEvent1(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "addevent";
    }

    // Post path to create new events
    @PostMapping("/api/events")
    public String createEvent2(@RequestParam String eventTitle,
                              @RequestParam String eventDescription,
                               @RequestParam String eventTime,
                               @RequestParam String eventDate,
                              @RequestParam String eventDue,
                              @RequestParam String eventCategory) throws ChangeSetPersister.NotFoundException {

        // Find the category from the database
        Category existingCategory = categoryRepository.findByCategoryName(eventCategory)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<Category> existingCategories = new ArrayList<>();
        existingCategories.add(existingCategory);

        // Create a new Event
        Event newEvent = new Event();
        newEvent.setEventTitle(eventTitle);
        newEvent.setEventDescription(eventDescription);


        // Parse eventDate, eventDue and eventTime
        try {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("HH:mm");

            LocalTime lt = LocalTime.parse(eventTime, f2);

            LocalDate ld = LocalDate.parse(eventDate, f);
            LocalDate ld2 = LocalDate.parse(eventDue, f);

            newEvent.setEventTime(lt);
            newEvent.setEventDate(ld);
            newEvent.setEventDue(ld2);
        // Throws error if fails to parse the date to correct format
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing time: " + eventTime);
            System.err.println("Error parsing date: " + eventDate);
            System.err.println("Error parsing date: " + eventDue);
            e.printStackTrace();
            return "redirect:/events";
        }

        newEvent.setCategories(existingCategories);

        // Save the Event
        eventRepository.save(newEvent);

        return "redirect:/events";
    }

    // Shows single event by id
    @GetMapping("/events/{id}")
    public String getOneEvent(Model model, @PathVariable Long id) {
        model.addAttribute("events", eventRepository.getReferenceById(id));
        return "events";
    }

    // Delete single event by id
    @DeleteMapping("/api/events/{id}")
    public String deleteEvent(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);

        // If id found from the db, deletes it
        if (event.isPresent()) {
            eventRepository.deleteById(id);
            return "redirect:/events";
        // Otherwise redirect and do nothing
        } else {
            return "redirect:/events";
        }
    }

    // Update single event by id
    @PutMapping("/api/events/{id}")
    public String updateEvent(@RequestParam String eventTitle,
                              @RequestParam String eventDescription,
                              @RequestParam String eventTime,
                              @RequestParam String eventDate,
                              @RequestParam String eventDue,
                              @RequestParam String eventCategory,
                              @PathVariable Long id) throws ChangeSetPersister.NotFoundException {

        Event updateEvent = eventRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        updateEvent.setEventTitle(eventTitle);
        updateEvent.setEventDescription(eventDescription);

        // Parse the String eventDate to Java.time object ld
        try {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("HH:mm");

            LocalTime lt = LocalTime.parse(eventTime, f2);

            LocalDate ld = LocalDate.parse(eventDate, f);
            LocalDate ld2 = LocalDate.parse(eventDue, f);

            updateEvent.setEventTime(lt);
            updateEvent.setEventDate(ld);
            updateEvent.setEventDue(ld2);
        // Throws error if fails to parse the date to correct format
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing time: " + eventTime);
            System.err.println("Error parsing date: " + eventDate);
            System.err.println("Error parsing date: " + eventDue);
            e.printStackTrace();
            return "redirect:/events";
        }

        Category existingCategory = categoryRepository.findByCategoryName(eventCategory)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<Category> existingCategories = new ArrayList<>();
        existingCategories.add(existingCategory);

        updateEvent.setCategories(existingCategories);

        eventRepository.save(updateEvent);

        return "redirect:/events";
    }

    // Path to edit the event from the UI
    @GetMapping("/events/edit/{id}")
    public String editEvent(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("events", eventRepository.getReferenceById(id));
        model.addAttribute("categories", categoryRepository.findAll());
        return "editevent";
    }

    // Default path to show all events and their categories
    @GetMapping("/events")
    public String list(Model model) {
        model.addAttribute("events", eventRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "events";
    }

    // Redirect to default path
    @GetMapping("/")
    public String home() {
        return "redirect:/events";
    }
}