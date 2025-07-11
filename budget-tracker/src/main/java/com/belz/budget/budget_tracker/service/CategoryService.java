package com.belz.budget.budget_tracker.service;

import com.belz.budget.budget_tracker.domain.Category;
import com.belz.budget.budget_tracker.domain.User;
import com.belz.budget.budget_tracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public List<Category> findUserCategories() {
        User currentUser = userService.getCurrentUser();
        return categoryRepository.findByUserId(currentUser.getId());
    }

    public Category createCategory(String name) {
        User currentUser = userService.getCurrentUser();
        Category category = new Category();
        category.setName(name);
        category.setUser(currentUser);
        return categoryRepository.save(category);
    }
}