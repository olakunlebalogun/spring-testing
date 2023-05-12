package com.olakunle.service;

import com.olakunle.dto.request.ProductRequest;
import com.olakunle.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> listAllProducts();

    String deleteProduct(String productId);

    ProductResponse productUpdate(String productId, ProductRequest request);

    ProductResponse addProduct(ProductRequest request);

}
