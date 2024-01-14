package com.example.WeBank.service;

import com.example.WeBank.model.Customer;
import com.example.WeBank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Determine if a customer is eligible for a loan based on credit score, income, and other criteria.
    public boolean isEligibleForLoan(Customer customer) {
        // Eligibility criteria
        int minCreditScore = 700; // Minimum required credit score
        double minIncome = 50000.0; // Minimum required annual income
        double maxDebtToIncomeRatio = 0.4; // Maximum allowed debt-to-income ratio
        int minEmploymentYears = 2; // Minimum required years of employment

        // Retrieve customer information
        int customerCreditScore = customer.getCreditScore();
        double customerIncome = customer.getAnnualIncome();
        double customerDebt = customer.getDebt();
        int yearsOfEmployment = customer.getYearsOfEmployment();

        // Check eligibility criteria
        if (customerCreditScore >= minCreditScore &&
                customerIncome >= minIncome &&
                calculateDebtToIncomeRatio(customerIncome, customerDebt) <= maxDebtToIncomeRatio &&
                yearsOfEmployment >= minEmploymentYears) {
            // The customer meets all eligibility criteria
            return true;
        } else {
            // The customer does not meet one or more eligibility criteria
            return false;
        }
    }

    private double calculateDebtToIncomeRatio(double income, double debt) {
        if (income != 0.0) {
            return debt / income;
        }
        return 0.0;
    }
}

