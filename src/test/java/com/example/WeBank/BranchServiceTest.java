package com.example.WeBank;

import com.example.WeBank.model.Branch;
import com.example.WeBank.repository.BranchRepository;
import com.example.WeBank.service.BranchService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchService branchService;

    @Test
    public void testCreateBranch() {
        Branch branchToCreate = new Branch(1, "Sample Branch");

        when(branchRepository.save(branchToCreate)).thenReturn(branchToCreate);
        Branch createdBranch = branchService.createBranch(branchToCreate);

        assertEquals(branchToCreate, createdBranch);
        verify(branchRepository, times(1)).save(branchToCreate);
    }

    @Test
    public void testFindByBranchId() {
        Branch expectedBranch = new Branch(1, "Sample Branch");

        when(branchRepository.findByBranchId(1)).thenReturn(expectedBranch);
        Branch foundBranch = branchService.findByBranchId(1);

        assertEquals(expectedBranch, foundBranch);
        verify(branchRepository, times(1)).findByBranchId(1);
    }
}