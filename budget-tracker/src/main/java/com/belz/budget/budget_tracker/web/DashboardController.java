package com.belz.budget.budget_tracker.web;

import com.belz.budget.budget_tracker.service.AccountService;
import com.belz.budget.budget_tracker.service.CategoryService;
import com.belz.budget.budget_tracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    @GetMapping({"/", "/dashboard"})
    public String showDashboard(Model model) {
        model.addAttribute("accounts", accountService.findUserAccounts());
        model.addAttribute("transactions", transactionService.findUserTransactions());
        model.addAttribute("categories", categoryService.findUserCategories());
        return "dashboard";
    }
}