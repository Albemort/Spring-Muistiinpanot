package com.example.Muistiinpanot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Autowired
    public DatabaseInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if categories are already in the database
        if (categoryRepository.count() == 0) {
            // Insert categories if the database is empty
            Category category1 = new Category("School");
            Category category2 = new Category("Work");
            Category category3 = new Category("Hobby");

            categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);

            System.out.println("Default categories inserted into the database.");
        }
    }
}