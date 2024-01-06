package com.example.WeBank.controller;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.AccountRequestBody;
import com.example.WeBank.model.Branch;
import com.example.WeBank.service.AccountService;
import com.example.WeBank.service.BranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/account")
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
public class AccountController {

    private AccountService accountService;
    private BranchService branchService;

    public AccountController(AccountService accountService, BranchService branchService) {
        this.accountService = accountService;
        this.branchService = branchService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequestBody account) {
        Integer branchId = account.getBranchId();
        Branch branch = branchService.findByBranchId(branchId);

        Account newAccount = account.getAccountFromAccountRequest(branch);
        newAccount.setAccountNumber();

        newAccount = accountService.createAccountWithUniqueAccountNumber(newAccount);

        Account createdAccount = accountService.createAccount(newAccount);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }
}
