package com.olakunle.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getListOfProducts() {
    }

    @Test
    void addProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void getOneProduct() {
    }

    @Test
    void updateCustomer() {
    }
}