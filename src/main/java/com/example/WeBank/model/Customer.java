package com.example.WeBank.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Customer {

    @Id
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "mobile_number", length = 15)
    private String mobileNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_number", referencedColumnName = "account_number",  nullable = false)
    private Account account;
}

