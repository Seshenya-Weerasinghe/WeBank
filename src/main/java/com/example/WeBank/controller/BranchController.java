package com.example.WeBank.controller;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.AccountRequestBody;
import com.example.WeBank.model.Branch;
import com.example.WeBank.service.AccountService;
import com.example.WeBank.service.BranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/branch")
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
public class BranchController {

    private BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody Branch branch) {

        Branch createdBranch = branchService.createBranch(branch);
        return new ResponseEntity<>(createdBranch, HttpStatus.CREATED);
    }
}
