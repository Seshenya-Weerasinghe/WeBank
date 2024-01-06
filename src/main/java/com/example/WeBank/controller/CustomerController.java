package com.example.WeBank.controller;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.Customer;
import com.example.WeBank.model.CustomerRequestBody;
import com.example.WeBank.service.AccountService;
import com.example.WeBank.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/customer")
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
public class CustomerController {

    private CustomerService customerService;
    private AccountService accountService;

    public CustomerController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Customer> createAccount(@RequestBody CustomerRequestBody customer) {
        String accountNumber = customer.getAccountNumber();
        Account account = accountService.findByAccountNumber(accountNumber);

        Customer newCustomer = customer.getCustomerFromCustomerRequest(account);
        Customer createdCustomer = customerService.createCustomer(newCustomer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }
}
