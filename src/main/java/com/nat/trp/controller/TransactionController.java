package com.nat.trp.controller;

import com.nat.trp.entity.Account;
import com.nat.trp.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/accounts/{acctId}")
    public List<Account> getAccount(@PathVariable("acctId") String acctId) {
        Long accountId = Long.parseLong(acctId);
        return accountService.getAllAccounts().stream()
                .filter(a -> accountId.equals(a.getAcctId()))
                .collect(Collectors.toList());
    }

}

