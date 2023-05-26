package com.olakunle.service.implementation;

import com.olakunle.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ProductServiceImplTest {

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        ProductServiceImpl productServiceImpl = new ProductServiceImpl(productRepository);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void listAllProducts() {
        // Given, When, Then
        // Arrange, Act, Assert
        // Use @SQL annotations
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void productUpdate() {
    }

    @Test
    void addProduct() {
    }

    @Test
    void getSingleProduct() {
    }

    @Test
    void updateCustomer() {
    }
}