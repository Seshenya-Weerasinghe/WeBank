package com.example.WeBank.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Branch {

    @Id
    @Column(name = "branch_id", nullable = false)
    private Integer branchId;

    @Column(name = "branch_name", length = 50)
    private String branchName;

}