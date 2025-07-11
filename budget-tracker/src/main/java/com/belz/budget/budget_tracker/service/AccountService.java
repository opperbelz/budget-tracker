package com.belz.budget.budget_tracker.service;

import com.belz.budget.budget_tracker.domain.Account;
import com.belz.budget.budget_tracker.domain.User;
import com.belz.budget.budget_tracker.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    public List<Account> findUserAccounts() {
        User currentUser = userService.getCurrentUser();
        return accountRepository.findByUserId(currentUser.getId());
    }

    public Account createAccount(String name) {
        User currentUser = userService.getCurrentUser();
        Account account = new Account();
        account.setName(name);
        account.setUser(currentUser);
        return accountRepository.save(account);
    }
}