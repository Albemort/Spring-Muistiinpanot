package com.example.Muistiinpanot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventControllerUnitTest {

    private EventController eventController;

    private EventRepository eventRepository;
    private CategoryRepository categoryRepository;
    private Model model;

    @BeforeEach
    public void setUp() {
        eventRepository = mock(EventRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        model = mock(Model.class);
        eventController = new EventController(eventRepository, categoryRepository);
    }

    @Test
    public void testCreateEvent() throws ChangeSetPersister.NotFoundException {
        // Mock data
        when(categoryRepository.findByCategoryName("TestCategory"))
                .thenReturn(Optional.of(new Category("TestCategory")));

        // Test createEvent2 method
        String result = eventController.createEvent2("Test Event", "Test Description", "2023-01-01", "TestCategory");

        // Verify that save method is called
        verify(eventRepository).save(any(Event.class));
        assertEquals("redirect:/events", result);
    }

    @Test
    public void testDeleteEvent() {
        Long eventId = 1L;
        // Mock data
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(new Event()));

        // Test deleteEvent method
        String result = eventController.deleteEvent(eventId);

        // Verify that deleteById method is called
        verify(eventRepository).deleteById(eventId);
        assertEquals("redirect:/events", result);
    }

    @Test
    public void testUpdateEvent() throws ChangeSetPersister.NotFoundException {
        Long eventId = 1L;
        // Mock data
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(new Event()));
        when(categoryRepository.findByCategoryName("TestCategory"))
                .thenReturn(Optional.of(new Category("TestCategory")));

        // Test updateEvent method
        String result = eventController.updateEvent("Updated Event", "Updated Description", "2023-01-01", "TestCategory", eventId);

        // Verify that save method is called
        verify(eventRepository).save(any(Event.class));
        assertEquals("redirect:/events", result);
    }


    @Test
    public void testList() {
        // Mock data
        when(eventRepository.findAll()).thenReturn(Collections.emptyList());
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // Test list method
        String result = eventController.list(model);

        // Verify that addAttribute method is called with the correct parameters
        verify(model).addAttribute(eq("events"), anyList());
        verify(model).addAttribute(eq("categories"), anyList());
        assertEquals("events", result);
    }
}
