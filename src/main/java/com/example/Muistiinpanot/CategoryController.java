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

    @GetMapping("/addcategory")
    public String addcategory(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "addcategory";
    }

    @PostMapping("/category/create")
    public String createCategory(@RequestParam String category) {
        try {
            Category existingCategory = categoryRepository.findByCategoryName(category).orElse(null);

            if (existingCategory == null) {
                Category newCategory = new Category();
                newCategory.setCategoryName(category);
                categoryRepository.save(newCategory);
            }

        } catch (Exception e) {
            System.err.println("Exception error: " + e);
            e.printStackTrace();
        }
        return "redirect:/addcategory";
    }
}
