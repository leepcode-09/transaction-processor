package com.nat.trp.controller;

import com.nat.trp.entity.Account;
import com.nat.trp.entity.AccountDetails;
import com.nat.trp.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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
        if (account == null) {
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

        // validate the incoming account details
        validateAccountDetails(accountDetails);

        return accountService.saveAccountDetails(accountDetails);
    }

    @PutMapping("/accounts/{acctId}/acctDetails")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDetails updateAccountDetails(@RequestBody AccountDetails accountDetails) {

        // validate the incoming account details
        validateAccountDetails(accountDetails);

        if (accountDetails.getTranType().equalsIgnoreCase("CR")
                || accountDetails.getTranType().equalsIgnoreCase("WD")) {
            if (accountDetails.getTranAmount().signum() == 0 || accountDetails.getTranAmount().signum() == -1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tranAmount must be greater than > 0.00 for CR or WD transaction types");
            }
        }

        if (accountDetails.getTranType().equalsIgnoreCase("DB")) {
            if (accountDetails.getTranAmount().signum() == 0 || accountDetails.getTranAmount().signum() == 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tranAmount must be less than < 0.00 for DB transaction type");
            }
        }

        BigDecimal totalBalance;
        List<Account> accounts = getAccount(accountDetails.getAcctId().toString());

        if (!accounts.isEmpty()) {
            // Had to write defensive coding because I have single accounts which are created as only Savings.
            // Some acconts are created as both Checking and Savings. So I have to check for both types of accounts before I can proceed with the transaction logic.
            // CH -> Checking Account, SV -> Savings Account, DB -> Debit, CR -> Credit, WD -> Withdrawal
            Account savingsAccount = accounts.stream().filter(a -> a.getAcctType().equalsIgnoreCase("SV")).findFirst().orElse(null);
            Account checkingAccount = accounts.stream().filter(a -> a.getAcctType().equalsIgnoreCase("CH")).findFirst().orElse(null);
            if ("SV".equalsIgnoreCase(savingsAccount.getAcctType())) {
                if (accountDetails.getTranType().equalsIgnoreCase("CR")) {
                    BigDecimal currAcctBal = savingsAccount.getAcctBalance();
                    totalBalance = currAcctBal.add(accountDetails.getTranAmount());
                    accountDetails.setTranAmount(totalBalance);
                } else if (accountDetails.getTranType().equalsIgnoreCase("DB")) {
                    BigDecimal currAcctBal = savingsAccount.getAcctBalance();
                    totalBalance = currAcctBal.divide(accountDetails.getTranAmount());
                    accountDetails.setTranAmount(totalBalance);
                } else if (accountDetails.getTranType().equalsIgnoreCase("WD")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not allowed at this point");
                }
            } else if ("CH".equalsIgnoreCase(checkingAccount.getAcctType())) {
                if (accountDetails.getTranType().equalsIgnoreCase("CR")) {
                    BigDecimal currAcctBal = checkingAccount.getAcctBalance();
                    totalBalance = currAcctBal.add(accountDetails.getTranAmount());
                    accountDetails.setTranAmount(totalBalance);
                } else if (accountDetails.getTranType().equalsIgnoreCase("DB")) {
                    BigDecimal currAcctBal = checkingAccount.getAcctBalance();
                    totalBalance = currAcctBal.divide(accountDetails.getTranAmount());
                    accountDetails.setTranAmount(totalBalance);
                } else if (accountDetails.getTranType().equalsIgnoreCase("WD")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not allowed at this point");
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Accounts exists with acctId " + accountDetails.getAcctId());
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

    private void validateAccountDetails(AccountDetails accountDetails) {

        if (accountDetails == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AccountDetails must not be null");
        }
        if (accountDetails.getAcctId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "acctId must not be null for AccountDetails");
        }

        // New validations
        validateNotBlank("acctIdentId", String.valueOf(accountDetails.getAcctIdentId()));
        validateNotBlank("tranType", accountDetails.getTranType());
        validateNotNull("updatedAcctBal", accountDetails.getTranAmount());
        validateNotBlank("tranTime", String.valueOf(accountDetails.getTranTime()));

    }

}

