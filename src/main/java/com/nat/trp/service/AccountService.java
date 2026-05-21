package com.nat.trp.service;

import com.nat.trp.entity.Account;
import com.nat.trp.entity.AccountDetails;
import com.nat.trp.entity.AccountDetailsRepository;
import com.nat.trp.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountDetailsRepository accountDetailsRepository;

    public AccountService(AccountRepository accountRepository,
                          AccountDetailsRepository accountDetailsRepository) {
        this.accountRepository = accountRepository;
        this.accountDetailsRepository = accountDetailsRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
    public AccountDetails saveAccountDetails(AccountDetails accountDetails) {
        return accountDetailsRepository.save(accountDetails);
    }


}


