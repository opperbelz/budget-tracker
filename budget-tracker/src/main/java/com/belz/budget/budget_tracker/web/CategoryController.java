package com.belz.budget.budget_tracker.web;

import com.belz.budget.budget_tracker.service.CategoryService;
import com.belz.budget.budget_tracker.web.dto.CategoryCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String showCategories(Model model) {
        model.addAttribute("categoryDto", new CategoryCreateDto());
        model.addAttribute("categories", categoryService.findUserCategories());
        return "categories";
    }

    @PostMapping("/categories")
    public String addCategory(@Valid @ModelAttribute("categoryDto") CategoryCreateDto categoryDto,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("categories", categoryService.findUserCategories());
            return "categories";
        }

        categoryService.createCategory(categoryDto.getName());
        return "redirect:/categories";
    }
}