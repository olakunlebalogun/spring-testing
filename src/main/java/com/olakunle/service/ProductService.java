package com.olakunle.service;

import com.github.fge.jsonpatch.JsonPatch;
import com.olakunle.dto.request.ProductRequest;
import com.olakunle.dto.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService {
    List<ProductResponse> listAllProducts();

    String deleteProduct(String productId);

    ProductResponse productUpdate(String productId, ProductRequest request);

    ProductResponse addProduct(ProductRequest request);

    ProductResponse getSingleProduct(String id);
    ResponseEntity<ProductResponse> updateCustomer(String id,JsonPatch patch);
}
