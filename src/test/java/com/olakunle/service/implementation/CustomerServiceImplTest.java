package com.olakunle.service.implementation;

import com.olakunle.dto.request.CustomerRequest;
import com.olakunle.dto.response.CustomerResponse;
import com.olakunle.model.Customer;
import com.olakunle.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

//    private CustomerRepository customerRepository;
//    private ModelMapper modelMapper;
//
//    private CustomerServiceImpl customerServiceImpl;
//    @BeforeEach
//    void setUp() {
//        customerRepository = Mockito.mock(CustomerRepository.class);
//        modelMapper = Mockito.mock(ModelMapper.class);
//        customerServiceImpl = new CustomerServiceImpl(customerRepository, modelMapper);
//    }
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;


    String email = "edsheeran23@gmail.com";
    String badEmail = "alonso23";
    String firstName = "Ed";
    String lastName = "Sheeran";
    String password = "passenger";

    // Model sample data
    Customer savedCustomer = new Customer(1L, email, firstName, lastName, password);
    // Creating request data
    CustomerRequest request = new CustomerRequest(
            email, firstName, lastName, password
    );
    CustomerRequest invalidRequest = new CustomerRequest(
            badEmail, firstName, lastName, password
    );

    // Creating response data
    CustomerResponse response = new CustomerResponse(
            1L, email, firstName, lastName
    );

    // List of Customers
    List<CustomerResponse> customerResponseList = List.of(
            CustomerResponse.builder()
                    .id(1L)
                    .firstName("Sherlock")
                    .lastName("Holmes")
                    .email("sherlockholmes@gmail.com")
                    .build(),

            CustomerResponse.builder()
                    .id(2L)
                    .firstName("Gerrard")
                    .lastName("Butler")
                    .email("gerrardbulter@yahoo.com")
                    .build()
    );

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testAddCustomer() {

        // Configure the mock customer repository
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(savedCustomer);

        // Call the add method on the customer service
        CustomerResponse response = customerServiceImpl.add(request);

        // Verify the repository method was called with the correct arguments
        Mockito.verify(customerRepository).save(Mockito.any(Customer.class));

        // Verify the response contains the expected data
        assertEquals(savedCustomer.getId(), response.getId());
        assertEquals(savedCustomer.getFirstName(), response.getFirstName());
        // ...
    }

    @Test
    void add() {

        //
//        BDDMockito.given(customerRepository.save(modelMapper.map(request, Customer.class))).willReturn();
//        when(customerServiceImpl.add(request)).thenReturn(response);


        CustomerResponse savedCustomer = customerServiceImpl.add(request);
        assertThat(savedCustomer.getEmail()).isNotNull();

    }



}