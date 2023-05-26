package com.olakunle.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olakunle.dto.request.CustomerRequest;
import com.olakunle.dto.response.CustomerResponse;
import com.olakunle.exception.CustomerNotFoundException;
import com.olakunle.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Slf4j
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    public static final String basePath = "/customers";

    String email = "edsheeran23@gmail.com";
    String badEmail = "alonso23";
    String firstName = "Ed";
    String lastName = "Sheeran";
    String password = "passenger";

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





    @BeforeEach
    void init() {
//        String email = "edsheeran23@gmail.com";
//        String firstName = "Ed";
//        String lastName = "Sheeran";
//        String password = "passenger";
         // Test: Logging the values we have in the Setup method
        log.error("######## Logging Setup Method ############");
        log.info("Name: {}, {}; email: {} ",  firstName, lastName, email);

//        // Creating request data
//        CustomerRequest request = new CustomerRequest(
//                email, firstName, lastName, password
//        );
//
//        // Creating response data
//        CustomerResponse response = new CustomerResponse(
//                1L, email, firstName, lastName
//        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void test_AddCustomer_Okay () throws Exception {

        // Act
        when(customerService.add(request)).thenReturn(response);


        // Act and Assert
        mockMvc.perform(post(basePath).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(header().string("Location", "/customers/1"))
//                .andReturn();
                .andDo(print());
    }

    @Test
    public void test_AddCustomer_InvalidData () throws Exception {


        // Act
//        when(customerService.add(request)).thenReturn(response);


        // Act and Assert
        MvcResult mvcResult = mockMvc.perform(post(basePath).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect((ResultMatcher) jsonPath("$.error", "Bad Request"))
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(header().string("Location", "/customers/1"))
                .andDo(print())
                .andReturn();
//        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringWhitespace()

    }

    @Test
    void testGetSingleCustomer() throws Exception {

        when(customerService.get(1L)).thenReturn(response);
        MvcResult mvcResult = mockMvc.perform(get("/customers/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").isString())
                .andExpect(jsonPath("$.firstName", "Ed").isString())
                .andExpect(jsonPath("$.lastName", "sheeran").isString())
                .andDo(print())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(response));
    }

    @Test
    void testGetAllCustomers() throws Exception{
        when(customerService.list()).thenReturn(customerResponseList);

        MvcResult mvcResult = mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(customerResponseList));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        when(customerService.delete("1")).thenReturn("Customer with ID 1 has been successfully deleted");

        MvcResult mvcResult = mockMvc.perform(delete("/customers/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer with ID 1 has been successfully deleted"))
                .andDo(print())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringWhitespace("Customer with ID 1 has been successfully deleted");

    }

//    @Test
//    void testDeleteIdNotValid() throws Exception {
//        String invalidId = "34";
//        when(customerService.delete(invalidId)).thenThrow(new CustomerNotFoundException(String.format("Customer with ID %s has been successfully deleted", invalidId)));
//
//        MvcResult mvcResult = mockMvc.perform(delete("/customers/{id}", invalidId))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Invalid ID"))
//                .andDo(print())
//                .andReturn();
//
////        assertThat(mvcResult.getResponse().getContentAsString()).isInstanceOfAny(CustomerNotFoundException.class);
//
//    }
}