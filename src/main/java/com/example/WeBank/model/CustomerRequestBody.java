package com.example.WeBank.model;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CustomerRequestBody {

    private Integer customerId;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String mobileNumber;

    private String accountNumber;

    private Integer creditScore;

    private Double annualIncome;

    private Double debt;

    private Integer yearsOfEmployment;

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", dateOfBirth=" + dateOfBirth +
                ", mobileNumber=" + mobileNumber +
                ", accountNumber='" + accountNumber +
                ", creditScore='" + creditScore +
                ", annualIncome='" + annualIncome +
                ", debt='" + debt +
                ", yearsOfEmployment='" + yearsOfEmployment + '\'' +
                '}';
    }

    public Customer getCustomerFromCustomerRequest(Account account) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setDateOfBirth(dateOfBirth);
        customer.setMobileNumber(mobileNumber);
        customer.setAccount(account);
        customer.setCreditScore(creditScore);
        customer.setAnnualIncome(annualIncome);
        customer.setDebt(debt);
        customer.setYearsOfEmployment(yearsOfEmployment);
        return customer;
    }
}