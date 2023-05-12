package com.olakunle.controllers;


import com.olakunle.dto.request.ProductRequest;
import com.olakunle.dto.response.ProductResponse;
import com.olakunle.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> getListOfProducts() {
        return ResponseEntity.ok().body(productService.listAllProducts());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request){
        return ResponseEntity.created(URI.create("/products")).body(productService.addProduct(request));
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteProduct(@PathVariable String productId){
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> updateProduct (@PathVariable String productId, @RequestBody ProductRequest request){
        return ResponseEntity.ok().body(productService.productUpdate(productId, request));
    }

    @GetMapping ("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> getOneProduct (@PathVariable String productId) {
        return ResponseEntity.ok().body(productService.getSingleProduct(productId));
    }


}
