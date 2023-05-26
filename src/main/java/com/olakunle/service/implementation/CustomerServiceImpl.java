package com.olakunle.service.implementation;

import com.olakunle.dto.request.CustomerRequest;
import com.olakunle.dto.response.CustomerResponse;
import com.olakunle.exception.CustomerNotFoundException;
import com.olakunle.model.Customer;
import com.olakunle.repository.CustomerRepository;
import com.olakunle.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public CustomerResponse add(CustomerRequest customer) {
        Customer savedCustomer = customerRepository.save(modelMapper.map(customer, Customer.class));
        return modelMapper.map(savedCustomer, CustomerResponse.class);

    }

    @Override
    public CustomerResponse update(String id, CustomerRequest user) throws CustomerNotFoundException {
        if (!customerRepository.existsByEmail(user.getEmail()) || !customerRepository.existsById(Long.valueOf(id))) {
//            customerRepository.save(modelMapper.map(user, Customer.class));
            throw new CustomerNotFoundException(String.format("Customer with Email %s does not exist", user.getEmail()));
        }
        Customer savedCustomer = customerRepository.save(modelMapper.map(user, Customer.class));
        return modelMapper.map(savedCustomer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse get(Long id) throws CustomerNotFoundException {
        Optional<Customer> result = customerRepository.findById(id);

        if (result.isPresent()) {
            return modelMapper.map(result.get(), CustomerResponse.class);
        }
        throw new CustomerNotFoundException(String.format("Customer with ID %s does not exists", id));
    }

    @Override
    public List<CustomerResponse> list() {
        return customerRepository.findAll().stream().map(i -> modelMapper.map(i, CustomerResponse.class)).collect(Collectors.toList());
    }

    @Override
    public String delete(String id) throws CustomerNotFoundException {
        if (customerRepository.existsById(Long.valueOf(id))) {
            customerRepository.deleteById(Long.valueOf(id));
            return String.format("Customer with ID %s has been successfully deleted", id);
        }
        throw new CustomerNotFoundException(String.format("Customer with ID %s has been successfully deleted", id));
    }

}
