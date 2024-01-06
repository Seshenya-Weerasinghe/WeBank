package com.example.WeBank.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "account_number", unique = true, nullable = false, length = 15)
    private String accountNumber;

    @Column(name = "account_balance")
    private Double accountBalance;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id",  nullable = false)
    private Branch branch;

    @Column(name = "account_type", length = 20)
    private String accountType;

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountBalance=" + accountBalance +
                ", branch=" + branch +
                ", accountType='" + accountType + '\'' +
                '}';
    }

    public void setAccountNumber() {
        long timestamp = Instant.now().toEpochMilli();
        this.accountNumber = "AC" + timestamp;
    }
}