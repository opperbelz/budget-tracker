package com.belz.budget.budget_tracker.service;

import com.belz.budget.budget_tracker.domain.Account;
import com.belz.budget.budget_tracker.domain.Category;
import com.belz.budget.budget_tracker.domain.Transaction;
import com.belz.budget.budget_tracker.domain.User;
import com.belz.budget.budget_tracker.repository.AccountRepository;
import com.belz.budget.budget_tracker.repository.CategoryRepository;
import com.belz.budget.budget_tracker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public List<Transaction> findUserTransactions() {
        User currentUser = userService.getCurrentUser();
        return transactionRepository.findByUserIdOrderByDateDesc(currentUser.getId());
    }

    @Transactional
    public void createTransaction(BigDecimal amount, LocalDate date, String description, Long accountId, Long categoryId) {
        User currentUser = userService.getCurrentUser();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account ID"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setDescription(description);
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setUser(currentUser);

        transactionRepository.save(transaction);

        // Обновляем баланс счета
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
}