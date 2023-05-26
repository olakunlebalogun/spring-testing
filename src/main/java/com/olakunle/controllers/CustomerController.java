package com.olakunle.controllers;

import com.olakunle.dto.request.CustomerRequest;
import com.olakunle.dto.response.CustomerResponse;
import com.olakunle.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
//    private final ModelMapper modelMapper;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> add(@RequestBody @Valid CustomerRequest request) {
        CustomerResponse addedCustomer = customerService.add(request);
        return ResponseEntity.created(URI.create("/customers/" + addedCustomer.getId())).body(addedCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(customerService.get(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> list() {
        if (!customerService.list().isEmpty()){
            return ResponseEntity.ok().body(customerService.list());
        }else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id", required = false) String id, @RequestBody CustomerRequest request) {
        return ResponseEntity.ok().body(customerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return ResponseEntity.ok().body(customerService.delete(id));
    }


}
