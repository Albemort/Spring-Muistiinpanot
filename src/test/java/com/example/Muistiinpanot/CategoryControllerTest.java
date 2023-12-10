package com.example.Muistiinpanot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory1() {
        // Mock data
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // Test createCategory1 method
        String result = categoryController.createCategory1(model);

        // Verify that addAttribute method is called with the correct parameters
        verify(model).addAttribute(eq("categories"), anyList());
        assertEquals("addcategory", result);
    }

    @Test
    public void testCreateCategory2() {
        // Mock data
        String categoryName = "TestCategory";
        when(categoryRepository.findByCategoryName(categoryName)).thenReturn(Optional.empty());

        // Test createCategory2 method
        String result = categoryController.createCategory2(categoryName);

        // Verify that save method is called with the correct parameters
        verify(categoryRepository).save(any(Category.class));
        assertEquals("redirect:/category/create", result);
    }

    @Test
    public void testCreateCategory2Duplicate() {
        // Mock data
        String categoryName = "TestCategory";
        when(categoryRepository.findByCategoryName(categoryName)).thenReturn(Optional.of(new Category()));

        // Test createCategory2 method for duplicate category
        String result = categoryController.createCategory2(categoryName);

        // Verify that save method is not called (since category already exists)
        verify(categoryRepository, never()).save(any(Category.class));
        assertEquals("redirect:/category/create", result);
    }
}
