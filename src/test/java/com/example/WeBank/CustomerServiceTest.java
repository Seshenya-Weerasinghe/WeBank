package com.example.WeBank;

import com.example.WeBank.model.Account;
import com.example.WeBank.model.Branch;
import com.example.WeBank.model.Customer;
import com.example.WeBank.model.CustomerRequestBody;
import com.example.WeBank.repository.CustomerRepository;
import com.example.WeBank.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
        Branch sampleBranch = new Branch(1, "Sample Branch");
        Account sampleAccount = new Account(1, "123456781234",1000.0, sampleBranch, "savings");

        CustomerRequestBody customerRequestBody = new CustomerRequestBody(1, "John", "Doe", LocalDate.of(1990, 1, 1), "1234567890", "123456781234");

        Customer expectedCustomer = customerRequestBody.getCustomerFromCustomerRequest(sampleAccount);

        when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);
        Customer createdCustomer = customerService.createCustomer(expectedCustomer);

        assertEquals(expectedCustomer, createdCustomer);
        verify(customerRepository, times(1)).save(expectedCustomer);
    }
}