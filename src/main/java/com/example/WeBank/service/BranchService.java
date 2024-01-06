package com.example.WeBank.service;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.Branch;
import com.example.WeBank.repository.BranchRepository;
import org.springframework.stereotype.Service;

@Service
public class BranchService {
    BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public Branch findByBranchId(Integer branchId) {
        return branchRepository.findByBranchId(branchId);
    }
}

