package com.olakunle.service.implementation;


import com.olakunle.dto.request.CustomerRequest;
import com.olakunle.dto.response.CustomerResponse;
import com.olakunle.exception.CustomerNotFoundException;
import com.olakunle.model.Customer;
import com.olakunle.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

//@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
public class GPTCustomerServiceImplTest {

//    @Mock
//    private CustomerRepository customerRepository;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private CustomerServiceImpl customerServiceImpl;

    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;

    private CustomerServiceImpl customerServiceImpl;
    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        customerServiceImpl = new CustomerServiceImpl(customerRepository, modelMapper);
    }


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


    @Test
    public void testAddCustomer() {
        // Configure the mock modelMapper
        Mockito.when(modelMapper.map(eq(request), eq(Customer.class))).thenReturn(savedCustomer);

        // Configure the mock customer repository
        Mockito.when(customerRepository.save(eq(savedCustomer))).thenReturn(savedCustomer);

        // Call the add method on the customer service
        CustomerResponse response = customerServiceImpl.add(request);

        // Verify the modelMapper was called with the correct arguments
        Mockito.verify(modelMapper).map(eq(request), eq(Customer.class));

        // Verify the repository method was called with the correct arguments
        Mockito.verify(customerRepository).save(eq(savedCustomer));

        // Verify the response contains the expected data
        assertEquals(savedCustomer.getId(), response.getId());
        assertEquals(savedCustomer.getFirstName(), response.getFirstName());
    }

    @Test
    public void testUpdateCustomer_ExistingCustomer() throws CustomerNotFoundException {
        // Create a mock customer request
        CustomerRequest request = new CustomerRequest();
        request.setEmail("john.doe@example.com");

        // Create a mock customer
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setEmail("john.doe@example.com");

        // Configure the mock customer repository
        Mockito.when(customerRepository.existsByEmail(eq(request.getEmail()))).thenReturn(true);
        Mockito.when(customerRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        // Call the update method on the customer service
        CustomerResponse response = customerServiceImpl.update("1", request);

        // Verify the repository methods were called with the correct arguments
        Mockito.verify(customerRepository).existsByEmail(eq(request.getEmail()));
        Mockito.verify(customerRepository).existsById(eq(1L));
        Mockito.verify(customerRepository).save(any(Customer.class));

        // Verify the response contains the expected data
        assertEquals(existingCustomer.getId(), response.getId());
        assertEquals(existingCustomer.getEmail(), response.getEmail());
    }

    @Test
    public void testUpdateCustomer_NonExistingCustomer() {
        // Create a mock customer request
        CustomerRequest request = new CustomerRequest();
        request.setEmail("john.doe@example.com");

        // Configure the mock customer repository
        Mockito.when(customerRepository.existsByEmail(eq(request.getEmail()))).thenReturn(false);
        Mockito.when(customerRepository.existsById(anyLong())).thenReturn(false);

        // Call the update method on the customer service and assert that it throws an exception
        assertThrows(CustomerNotFoundException.class, () -> customerServiceImpl.update("1", request));

        // Verify the repository methods were called with the correct arguments
        Mockito.verify(customerRepository).existsByEmail(eq(request.getEmail()));
        Mockito.verify(customerRepository).existsById(eq(1L));

        // Verify that the repository save method was not called
        Mockito.verify(customerRepository, Mockito.never()).save(any(Customer.class));
    }

    @Test
    public void testGetCustomer_ExistingCustomer() throws CustomerNotFoundException {
        // Create a mock customer


        // Configure the mock customer repository
        BDDMockito.given(customerRepository.findById(anyLong())).willReturn(Optional.of(savedCustomer));
//        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(savedCustomer));
//        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.of(savedCustomer));

        // Call the get method on the customer service
        CustomerResponse response = customerServiceImpl.get(1L);

        // Verify the repository method was called with the correct argument
        Mockito.verify(customerRepository).findById(eq(1L));

        // Verify the response contains the expected data
        assertEquals(savedCustomer.getId(), response.getId());
    }

    @Test
    public void testGetCustomer_NonExistingCustomer() {
        // Configure the mock customer repository
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call the get method on the customer service and assert that it throws an exception
        assertThrows(CustomerNotFoundException.class, () -> customerServiceImpl.get(1L));

        // Verify the repository method was called with the correct argument
        Mockito.verify(customerRepository).findById(eq(1L));
    }

    @Test
    public void testListCustomers() {
        // Create mock customers
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(2L);

        // Configure the mock customer repository
        Mockito.when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        // Call the list method on the customer service
        List<CustomerResponse> responseList = customerServiceImpl.list();

        // Verify the repository method was called
        Mockito.verify(customerRepository).findAll();

        // Verify the response list contains the expected number of items
        assertEquals(2, responseList.size());

        // Verify the response list contains the expected customer IDs
        assertEquals(customer1.getId(), responseList.get(0).getId());
        assertEquals(customer2.getId(), responseList.get(1).getId());
    }

    @Test
    public void testDeleteCustomer_ExistingCustomer() throws CustomerNotFoundException {
        // Configure the mock customer repository
        Mockito.when(customerRepository.existsById(eq(1L))).thenReturn(true);

        // Call the delete method on the customer service
        String result = customerServiceImpl.delete("1");

        // Verify the repository method was called with the correct argument
        Mockito.verify(customerRepository).existsById(eq(1L));
        Mockito.verify(customerRepository).deleteById(eq(1L));

        // Verify the result message
        assertEquals("Customer with ID 1 has been successfully deleted", result);
    }

    @Test
    public void testDeleteCustomer_NonExistingCustomer() {
        // Configure the mock customer repository
        Mockito.when(customerRepository.existsById(eq(1L))).thenReturn(false);

        // Call the delete method on the customer service and assert that it throws an exception
        assertThrows(CustomerNotFoundException.class, () -> customerServiceImpl.delete("1"));

        // Verify the repository method was called with the correct argument
        Mockito.verify(customerRepository).existsById(eq(1L));

        // Verify that the repository deleteById method was not called
        Mockito.verify(customerRepository, Mockito.never()).deleteById(eq(1L));
    }
}
