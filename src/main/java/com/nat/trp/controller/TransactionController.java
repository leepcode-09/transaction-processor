package com.nat.trp.controller;

import com.nat.trp.entity.Account;
import com.nat.trp.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tps/app")
public class TransactionController {

    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<Account> getAccounts() {
        return accountService.getAllAccounts();
    }
}

