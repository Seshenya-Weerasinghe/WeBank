package com.example.WeBank;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.Branch;
import com.example.WeBank.model.Customer;
import com.example.WeBank.model.CustomerRequestBody;
import com.example.WeBank.repository.CustomerRepository;
import com.example.WeBank.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
        Branch sampleBranch = new Branch(1, "Sample Branch");
        Account sampleAccount = new Account(1, "123456781234",1000.0, sampleBranch, "savings");

        CustomerRequestBody customerRequestBody = new CustomerRequestBody(1, "John", "Doe", LocalDate.of(1990, 1, 1), "1234567890", "123456781234", 900, 70000.0, 10000.0, 5);

        Customer expectedCustomer = customerRequestBody.getCustomerFromCustomerRequest(sampleAccount);

        when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);
        Customer createdCustomer = customerService.createCustomer(expectedCustomer);

        assertEquals(expectedCustomer, createdCustomer);
        verify(customerRepository, times(1)).save(expectedCustomer);
    }

    @Test
    public void test_IsEligibleForLoan_WithMarginalCreditScore() {
        // Arrange
        Customer customer = new Customer();
        customer.setCreditScore(680); // Below the minimum required credit score
        customer.setAnnualIncome(70000.0);
        customer.setDebt(10000.0);
        customer.setYearsOfEmployment(5);

        // Act
        boolean isEligible = customerService.isEligibleForLoan(customer);

        // Assert
        assertFalse(isEligible);
    }

    @Test
    public void test_IsEligibleForLoan_WithHighCreditScore() {
        // Arrange
        Customer customer = new Customer();
        customer.setCreditScore(800); // Above the minimum required credit score
        customer.setAnnualIncome(70000.0);
        customer.setDebt(10000.0);
        customer.setYearsOfEmployment(5);

        // Act
        boolean isEligible = customerService.isEligibleForLoan(customer);

        // Assert
        assertTrue(isEligible);
    }

    @Test
    public void test_IsEligibleForLoan_WithHighDebtToIncomeRatio() {
        // Arrange
        Customer customer = new Customer();
        customer.setCreditScore(720);
        customer.setAnnualIncome(80000.0);
        customer.setDebt(40000.0); // High debt relative to income
        customer.setYearsOfEmployment(7);

        // Act
        boolean isEligible = customerService.isEligibleForLoan(customer);

        // Assert
        assertFalse(isEligible);
    }

    @Test
    public void test_IsEligibleForLoan_WithInsufficientEmploymentHistory() {
        // Arrange
        Customer customer = new Customer();
        customer.setCreditScore(720);
        customer.setAnnualIncome(70000.0);
        customer.setDebt(10000.0);
        customer.setYearsOfEmployment(1); // Insufficient employment history

        // Act
        boolean isEligible = customerService.isEligibleForLoan(customer);

        // Assert
        assertFalse(isEligible);
    }

}