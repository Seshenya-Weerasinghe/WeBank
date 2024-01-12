package com.example.WeBank;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.Branch;
import com.example.WeBank.model.Transaction;
import com.example.WeBank.repository.AccountRepository;
import com.example.WeBank.repository.TransactionRepository;
import com.example.WeBank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testCalculateAccountCost() {
        Branch sampleBranch = new Branch(1, "Sample Branch");
        Account sampleAccount = new Account(1, "AC123456789", 1000.0, sampleBranch, "Savings");

        // Create sample transactions for the account
        Transaction depositTransaction = new Transaction();
        depositTransaction.setTransactionType("Deposit");
        depositTransaction.setTransactionAmount(1000.0); // $1000 deposit

        Transaction withdrawalTransaction = new Transaction();
        withdrawalTransaction.setTransactionType("Withdrawal");
        withdrawalTransaction.setTransactionAmount(500.0); // $500 withdrawal

        Transaction transferTransaction = new Transaction();
        transferTransaction.setTransactionType("Transfer");
        transferTransaction.setTransactionAmount(100.0); // $100 transfer

        // Add transactions to a list
        List<Transaction> transactions = List.of(depositTransaction, withdrawalTransaction, transferTransaction);

        // Mock the transactionRepository
        when(transactionRepository.findByAccountNumber(sampleAccount.getAccountNumber())).thenReturn(transactions);

        double monthlyCost = accountService.calculateAccountCost(sampleAccount);

        assertEquals(15.0, monthlyCost);
    }

    @Test
    public void testConvertCurrency() {

        // Test conversion from EUR to USD
        double amountInEURToUSD = accountService.convertCurrency(100.0, "EUR", "USD");
        assertEquals(118.0, amountInEURToUSD, 0.01);

        // Test conversion from EUR to GBP
        double amountInEURToGBP = accountService.convertCurrency(100.0, "EUR", "GBP");
        assertEquals(85.0, amountInEURToGBP, 0.01);

        // Test conversion from an invalid currency code
        assertThrows(IllegalArgumentException.class,
                () -> accountService.convertCurrency(100.0, "EUR", "INVALID"));
    }

    @Test
    public void testCalculateCompoundInterest() {
        double initialAmount = 1000.0;
        double interestRate = 5.0;
        int compoundingFrequency = 12;
        int years = 3;

        double compoundInterest = accountService.calculateCompoundInterest(initialAmount, interestRate, compoundingFrequency, years);

        // Expected compound interest calculated using the formula A = P(1 + r/n)^(nt) - P
        double expectedCompoundInterest = 12.576;

        assertEquals(expectedCompoundInterest, compoundInterest, 0.01);
    }

}
