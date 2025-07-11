package com.belz.budget.budget_tracker.web;

import com.belz.budget.budget_tracker.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/accounts")
    public String showAccounts(Model model) {
        model.addAttribute("accounts", accountService.findUserAccounts());
        return "accounts";
    }

    @PostMapping("/accounts")
    public String addAccount(@RequestParam String name) {
        accountService.createAccount(name);
        return "redirect:/accounts";
    }
}