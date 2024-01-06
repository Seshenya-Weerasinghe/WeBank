package com.example.WeBank.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class AccountRequestBody {

    private Integer accountId;

    private String accountNumber;

    private Double accountBalance;

    private Integer branchId;

    private String accountType;

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                "accountNumber=" + accountNumber +
                ", accountBalance=" + accountBalance +
                ", branchId=" + branchId +
                ", accountType='" + accountType + '\'' +
                '}';
    }

    public Account getAccountFromAccountRequest(Branch branch) {
        Account account = new Account();
        account.setAccountId(accountId);
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountType);
        account.setAccountBalance(accountBalance);
        account.setBranch(branch);
        return account;
    }
}