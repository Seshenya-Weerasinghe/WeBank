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
    public void testCreateAccount() {
        Branch sampleBranch = new Branch(1, "Sample Branch");
        Account sampleAccount = new Account(1, "AC123456789", 1000.0, sampleBranch, "savings");

        when(accountRepository.save(sampleAccount)).thenReturn(sampleAccount);

        Account createdAccount = accountService.createAccount(sampleAccount);

        assertEquals(sampleAccount, createdAccount);
        verify(accountRepository, times(1)).save(sampleAccount);
    }

    @Test
    public void testFindByAccountNumber() {
        Branch sampleBranch = new Branch(1, "Sample Branch");
        Account sampleAccount = new Account(1, "AC123456789", 1000.0, sampleBranch, "savings");

        when(accountRepository.findByAccountNumber("AC123456789")).thenReturn(sampleAccount);

        Account foundAccount = accountService.findByAccountNumber("AC123456789");

        assertEquals(sampleAccount, foundAccount);
        verify(accountRepository, times(1)).findByAccountNumber("AC123456789");
    }

//    @Test
//    public void testCreateAccountWithUniqueAccountNumber() {
//        Branch sampleBranch = new Branch(1, "Sample Branch");
//        Account sampleAccount = new Account(1, "AC123456789", 1000.0, sampleBranch, "savings");
//
//        when(accountRepository.findByAccountNumber("AC123456789")).thenReturn(null);
//
//    }

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
}
