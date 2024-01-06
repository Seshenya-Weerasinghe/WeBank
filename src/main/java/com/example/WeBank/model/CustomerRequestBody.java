package com.example.WeBank.model;

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

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", dateOfBirth=" + dateOfBirth +
                ", mobileNumber=" + mobileNumber +
                ", accountNumber='" + accountNumber + '\'' +
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
        return customer;
    }
}