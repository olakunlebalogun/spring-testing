package com.olakunle.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.olakunle.dto.request.ProductRequest;
import com.olakunle.dto.response.ProductResponse;
import com.olakunle.model.Product;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MapperClass {

    private static final ObjectMapper objectMapper = new ObjectMapper();
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

    public static Product applyPatchToProduct(JsonPatch patch, Product targetCustomer) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, Product.class);
    }

}
