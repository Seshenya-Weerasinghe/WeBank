package com.example.WeBank.service;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.Transaction;
import com.example.WeBank.repository.AccountRepository;
import com.example.WeBank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    AccountRepository accountRepository;
    TransactionRepository transactionRepository;

    private final Map<String, Double> sampleExchangeRates;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        // Populate sample exchange rates with EUR as the base currency
        this.sampleExchangeRates = new HashMap<>();
        sampleExchangeRates.put("EUR", 1.0);
        sampleExchangeRates.put("USD", 1.18);  // 1 EUR = 1.18 USD
        sampleExchangeRates.put("GBP", 0.85);  // 1 EUR = 0.85 GBP
        // Add more currencies as needed
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
        double WITHDRAWAL_FEE_FIXED = 2.0; // €2 fixed withdrawal fee
        double TRANSFER_FEE_FIXED = 1.0; // €1 fixed transfer fee

        if (account.getAccountType().equals("Checking")) {
            monthlyCost = 5.0;
        } else if (account.getAccountType().equals("Savings")) {
            monthlyCost = 2.0;
        } else if (account.getAccountType().equals("Investment")) {
            monthlyCost = 10.0;
        }

        // Calculate transaction fees based on account transactions
        List<Transaction> transactions = transactionRepository.findByAccountNumber(account.getAccountNumber());
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

    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        if (sampleExchangeRates.containsKey(fromCurrency) && sampleExchangeRates.containsKey(toCurrency)) {
            double exchangeRate = sampleExchangeRates.get(toCurrency) / sampleExchangeRates.get(fromCurrency);
            return amount * exchangeRate;
        } else {
            throw new IllegalArgumentException("Invalid currency codes");
        }
    }

    // Calculate the future value of an investment or loan using compound interest formulas
    public double calculateCompoundInterest(double initialAmount, double interestRate, int compoundingFrequency, int years) {

        if (initialAmount <= 0 || interestRate <= 0 || compoundingFrequency <= 0 || years <= 0) {
            throw new IllegalArgumentException("Invalid input parameters. Please provide positive values.");
        }

        // Calculate compound interest
        double n = compoundingFrequency;
        double r = interestRate / (n * 100); // Convert interest rate to decimal
        double nt = n * years;

        double compoundInterest = initialAmount * Math.pow((1 + r / n), nt) - initialAmount;

        return compoundInterest;
    }

}

