package com.belz.budget.budget_tracker.web;

import com.belz.budget.budget_tracker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public String addTransaction(@RequestParam BigDecimal amount,
                                 @RequestParam LocalDate date,
                                 @RequestParam String description,
                                 @RequestParam Long accountId,
                                 @RequestParam Long categoryId) {
        transactionService.createTransaction(amount, date, description, accountId, categoryId);
        return "redirect:/dashboard";
    }
}