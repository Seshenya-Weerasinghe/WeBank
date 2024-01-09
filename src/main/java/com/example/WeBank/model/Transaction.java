package com.example.WeBank.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Transaction {

    @Id
    @Column(name = "transaction_id", nullable = false)
    private Integer transactionId;

    @Column(name = "transaction_type", length = 20)
    private String transactionType;

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "transaction_time")
    private String transactionTime;

    @Column(name = "account_number", length = 20)
    private String accountNumber;

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", transactionType='" + transactionType + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", transactionDate='" + transactionDate + '\'' +
                ", transactionTime='" + transactionTime + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }

}
