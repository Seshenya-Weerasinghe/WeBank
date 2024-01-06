package com.example.WeBank;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.Branch;
import com.example.WeBank.repository.AccountRepository;
import com.example.WeBank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

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
}
