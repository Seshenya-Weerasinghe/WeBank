package com.example.WeBank.service;

import com.example.WeBank.model.Account;
import com.example.WeBank.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public Account createAccountWithUniqueAccountNumber(Account account) {
        while (true) {
            Account tempAcc = this.findByAccountNumber(account.getAccountNumber());
            if (tempAcc == null) {
                return account;
            } else {
                account.setAccountNumber();
            }
        }
    }

}

