package com.olakunle.service.implementation;

import com.olakunle.dto.request.CustomerRequest;
import com.olakunle.dto.response.CustomerResponse;
import com.olakunle.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;

    private CustomerServiceImpl customerServiceImpl;
    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        customerServiceImpl = new CustomerServiceImpl(customerRepository, modelMapper);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void add() {
        String email = "edsheeran23@gmail.com";
        String firstName = "Ed";
        String lastName = "Sheeran";
        String password = "passenger";

        // Creating request data
        CustomerRequest request = new CustomerRequest(
                email, firstName, lastName, password
        );

        // Creating response data
        CustomerResponse response = new CustomerResponse(
                1L, email, firstName, lastName
        );
        CustomerResponse savedCustomer = customerServiceImpl.add(request);
        assertThat(savedCustomer.getEmail()).isNotNull();

    }

    @Test
    void update() {
    }

    @Test
    void get() {
    }

    @Test
    void list() {
    }

    @Test
    void delete() {
    }
}