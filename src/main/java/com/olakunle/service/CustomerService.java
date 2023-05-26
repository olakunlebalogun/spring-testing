package com.olakunle.service;

import com.olakunle.dto.request.CustomerRequest;
import com.olakunle.dto.response.CustomerResponse;
import com.olakunle.exception.CustomerNotFoundException;
import com.olakunle.model.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse add(CustomerRequest user);

//    CustomerResponse update(CustomerRequest user) throws CustomerNotFoundException;

    CustomerResponse update(String id, CustomerRequest user) throws CustomerNotFoundException;

    CustomerResponse get(Long id) throws CustomerNotFoundException;
    List<CustomerResponse> list();
    String delete(String id) throws CustomerNotFoundException;
}
