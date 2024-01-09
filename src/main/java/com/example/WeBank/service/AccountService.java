package com.example.WeBank.service;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.Transaction;
import com.example.WeBank.repository.AccountRepository;
import com.example.WeBank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountService {
    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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

    public double calculateAccountCost(Account account){
        double monthlyCost = 0.0;
        double DEPOSIT_FEE_PERCENTAGE = 0.01; // 1% deposit fee
        double WITHDRAWAL_FEE_FIXED = 2.0; // $2 fixed withdrawal fee
        double TRANSFER_FEE_FIXED = 1.0; // $1 fixed transfer fee

        if (account.getAccountType().equals("Checking")) {
            monthlyCost = 5.0;
        } else if (account.getAccountType().equals("Savings")) {
            monthlyCost = 2.0;
        } else if (account.getAccountType().equals("Investment")) {
            monthlyCost = 10.0;
        }

        // Calculate transaction fees based on account transactions
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions) {
            switch (transaction.getTransactionType()) {
                case "Deposit" -> {
                    // Apply deposit fee
                    double depositFee = transaction.getTransactionAmount() * DEPOSIT_FEE_PERCENTAGE;
                    monthlyCost += depositFee;
                }
                case "Withdrawal" ->
                    // Apply withdrawal fee
                        monthlyCost += WITHDRAWAL_FEE_FIXED;
                case "Transfer" ->
                    // Apply transfer fee
                        monthlyCost += TRANSFER_FEE_FIXED;
            }
        }

        return monthlyCost;
    }

}

