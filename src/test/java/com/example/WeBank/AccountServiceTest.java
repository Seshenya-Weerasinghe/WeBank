package com.example.WeBank;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.Branch;
import com.example.WeBank.model.Transaction;
import com.example.WeBank.repository.AccountRepository;
import com.example.WeBank.repository.TransactionRepository;
import com.example.WeBank.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
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

    private Branch sampleBranch;

    @BeforeEach
    public void setUp(){
        sampleBranch = new Branch(1, "Sample Branch");
    }

    @Test
    public void testCreateAccount() {
        Account sampleAccount = new Account(1, "AC123456789", 1000.0, sampleBranch, "savings");

        when(accountRepository.save(sampleAccount)).thenReturn(sampleAccount);

        Account createdAccount = accountService.createAccount(sampleAccount);

        assertEquals(sampleAccount, createdAccount);
        verify(accountRepository, times(1)).save(sampleAccount);
    }

    @Test
    public void testFindByAccountNumber() {
        Account sampleAccount = new Account(1, "AC123456789", 1000.0, sampleBranch, "savings");

        when(accountRepository.findByAccountNumber("AC123456789")).thenReturn(sampleAccount);

        Account foundAccount = accountService.findByAccountNumber("AC123456789");

        assertEquals(sampleAccount, foundAccount);
        verify(accountRepository, times(1)).findByAccountNumber("AC123456789");
    }

    @Test
    public void test_ConvertCurrency_ToUSD() {

        // Test conversion from EUR to USD
        double amountInEURToUSD = accountService.convertCurrency(100.0, "EUR", "USD");
        assertEquals(118.0, amountInEURToUSD, 0.01);

    }

    @Test
    public void test_ConvertCurrency_ToGBP() {

        // Test conversion from EUR to GBP
        double amountInEURToGBP = accountService.convertCurrency(100.0, "EUR", "GBP");
        assertEquals(85.0, amountInEURToGBP, 0.01);

    }

    @Test
    public void test_ConvertCurrency_ToInvalidCurrencyType() {

        // Test conversion to an invalid currency code
        assertThrows(IllegalArgumentException.class,
                () -> accountService.convertCurrency(100.0, "EUR", "INVALID"));
    }

    @Test
    public void test_CalculateCompoundInterest_WithInitialAmount() {
        double initialAmount = 1000.0;
        double interestRate = 5.0;
        int compoundingFrequency = 12;
        int years = 3;

        double compoundInterest = accountService.calculateCompoundInterest(initialAmount, interestRate, compoundingFrequency, years);

        // Expected compound interest calculated using the formula A = P(1 + r/n)^(nt) - P
        double expectedCompoundInterest = 12.576;

        assertEquals(expectedCompoundInterest, compoundInterest, 0.01);
    }

    @Test
    public void test_CalculateCompoundInterest_WithNoInitialAmount() {
        double initialAmount = 0.0;
        double interestRate = 5.0;
        int compoundingFrequency = 12;
        int years = 3;

        // Test when initial amount is 0.0
        assertThrows(IllegalArgumentException.class,
                () -> accountService.calculateCompoundInterest(initialAmount, interestRate, compoundingFrequency, years));
    }

    @Test
    public void test_CalculateAccountCost_ForCheckingAccountWithNoTransactions() {
        Account checkingAccount = new Account(1, "AC123456789", 1000.0, sampleBranch, "Checking");
        when(transactionRepository.findByAccountNumber(anyString())).thenReturn(Arrays.asList());

        double cost = accountService.calculateAccountCost(checkingAccount);
        assertEquals(5.0, cost);
    }

    @Test
    public void test_CalculateAccountCost_ForSavingsAccountWithDepositTransactions() {
        Account savingsAccount = new Account(1, "AC123456789", 1000.0, sampleBranch, "Savings");
        Transaction depositTransaction = new Transaction();
        depositTransaction.setTransactionType("Deposit");
        depositTransaction.setTransactionAmount(100.0); // $100 deposit
        when(transactionRepository.findByAccountNumber(anyString())).thenReturn(Arrays.asList(depositTransaction));

        double cost = accountService.calculateAccountCost(savingsAccount);
        double expectedCost = 2.0 + 100.0 * 0.01; // 2.0 is the base cost, and 1% of 100 is the deposit fee
        assertEquals(expectedCost, cost);
    }

    @Test
    public void test_CalculateAccountCost_ForInvestmentAccountWithWithdrawalTransactions() {
        Account investmentAccount = new Account(1, "AC123456789", 1000.0, sampleBranch, "Investment");

        Transaction withdrawalTransaction = new Transaction();
        withdrawalTransaction.setTransactionType("Withdrawal");
        withdrawalTransaction.setTransactionAmount(100.0); // $100 withdrawal

        when(transactionRepository.findByAccountNumber(anyString())).thenReturn(Arrays.asList(withdrawalTransaction));

        double cost = accountService.calculateAccountCost(investmentAccount);
        double expectedCost = 10.0 + 2.0; // 10.0 is the base cost, and 2.0 is the withdrawal fee
        assertEquals(expectedCost, cost);
    }

    @Test
    public void test_CalculateAccountCost_ForCheckingAccountWithMixedTransactions() {
        Account checkingAccount = new Account(1, "AC123456789", 1000.0, sampleBranch, "Checking");

        Transaction depositTransaction = new Transaction();
        depositTransaction.setTransactionType("Deposit");
        depositTransaction.setTransactionAmount(100.0); // $100 deposit

        Transaction withdrawalTransaction = new Transaction();
        withdrawalTransaction.setTransactionType("Withdrawal");
        withdrawalTransaction.setTransactionAmount(100.0); // $100 withdrawal

        Transaction transferTransaction = new Transaction();
        transferTransaction.setTransactionType("Transfer");
        transferTransaction.setTransactionAmount(100.0); // $100 transfer

        when(transactionRepository.findByAccountNumber(anyString())).thenReturn(Arrays.asList(depositTransaction, withdrawalTransaction, transferTransaction));

        double cost = accountService.calculateAccountCost(checkingAccount);
        double expectedCost = 5.0 + 100.0 * 0.01 + 2.0 + 1.0; // 5.0 is the base cost, 1% of 100 is the deposit fee, 2.0 is the withdrawal fee, and 1.0 is the transfer fee
        assertEquals(expectedCost, cost);
    }
}
