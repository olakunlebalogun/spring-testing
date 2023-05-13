package com.olakunle.service;

import com.olakunle.dto.request.ProductRequest;
import com.olakunle.dto.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductResponse> listAllProducts();

    String deleteProduct(UUID productId);

    ProductResponse productUpdate(UUID productId, ProductRequest request);

    ProductResponse addProduct(ProductRequest request);

}
