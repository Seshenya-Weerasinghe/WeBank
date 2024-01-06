package com.example.WeBank.repository;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByAccountNumber(String accountNumber);
}
