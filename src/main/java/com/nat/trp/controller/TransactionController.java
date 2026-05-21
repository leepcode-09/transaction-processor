package com.nat.trp.controller;

import com.nat.trp.entity.Account;
import com.nat.trp.entity.AccountDetails;
import com.nat.trp.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody Account account) {
        if(account == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account must not be null");
        }
        if (account.getAcctId() == null || account.getAcctId().toString().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "acctId must not be null or empty");
        }

        // validate additional required string fields
        validateNotBlank("acctType", account.getAcctType());
        validateNotBlank("acctCurrencyType", account.getAcctCurrencyType());
        validateNotBlank("acctHolderFn", account.getAcctHolderFn());
        validateNotBlank("acctHolderLn", account.getAcctHolderLn());

        List<Account> existing = getAccount(account.getAcctId().toString());
        if (!existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Account with acctId " + account.getAcctId() + " already exists");
        }

        return accountService.saveAccount(account);
    }

    @PostMapping("/accounts/acctDetails")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDetails createAccountDetails(@RequestBody AccountDetails accountDetails) {
        if (accountDetails == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AccountDetails must not be null");
        }
        if (accountDetails.getAcctId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "acctId must not be null for AccountDetails");
        }

        // New validations
        validateNotBlank("acctIdentId", String.valueOf(accountDetails.getAcctIdentId()));
        validateNotBlank("tranType", accountDetails.getTranType());
        validateNotNull("updatedAcctBal", accountDetails.getUpdatedAcctBal());
        validateNotBlank("tranTime", String.valueOf(accountDetails.getTranTime()));

        // Credit, Debit and Withdrawal logic - Not implemented yet TODO
        if(accountDetails.getTranType().equalsIgnoreCase("CR")){
            // Not implemented yet TODO
        }else if(accountDetails.getTranType().equalsIgnoreCase("DB")){
            // Not implemented yet TODO
        }else if(accountDetails.getTranType().equalsIgnoreCase("WD")){
            // Not implemented yet TODO
        }

        return accountService.saveAccountDetails(accountDetails);
    }

    private void validateNotBlank(String fieldName, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " must not be null or empty");
        }
    }

    private void validateNotNull(String fieldName, Object value) {
        if (value == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " must not be null");
        }
    }


}

