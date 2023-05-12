package com.olakunle.utils;

import com.olakunle.dto.request.ProductRequest;
import com.olakunle.dto.response.ProductResponse;
import com.olakunle.model.Product;

public class MapperClass {

    public static ProductResponse mapToProductResponse (Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setProductName(product.getProductName());

        return response;
    }

    public static Product mapToProduct (ProductRequest request, Product product) {
//        Product product = new Product();
        product.setPrice(request.getPrice());
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());

        return product;
    }
}
