package com.example.Muistiinpanot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    // Get path to create new categories from the UI
    @GetMapping("/category/create")
    public String createCategory1(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "addcategory";
    }

    // Post path to create new categories and insert them to db
    @PostMapping("/api/category")
    public String createCategory2(@RequestParam String category) {
        // Try to find categories with the same name
        try {
            Category existingCategory = categoryRepository.findByCategoryName(category).orElse(null);

            // If duplicates not found, existingCategory = null
            // and save it to db.
            if (existingCategory == null) {
                Category newCategory = new Category();
                newCategory.setCategoryName(category);
                categoryRepository.save(newCategory);
            }
        // Throws error if category already exists and redirects back
        } catch (Exception e) {
            System.err.println("Exception error: " + e);
            e.printStackTrace();
            return "redirect:/category/create";
        }
        return "redirect:/category/create";
    }
}
