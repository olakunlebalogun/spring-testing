package com.olakunle.service.implementation;

import com.olakunle.dto.request.ProductRequest;
import com.olakunle.dto.response.ProductResponse;
import com.olakunle.exception.ProductNotFound;
import com.olakunle.model.Product;
import com.olakunle.repository.ProductRepository;
import com.olakunle.service.ProductService;
import com.olakunle.utils.MapperClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.olakunle.utils.MapperClass.mapToProduct;
import static com.olakunle.utils.MapperClass.mapToProductResponse;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> listAllProducts() {
        return productRepository.findAll().stream().map(MapperClass::mapToProductResponse).collect(Collectors.toList());
    }

    @Override
    public String deleteProduct(String productId) {
        UUID prodId = UUID.fromString(productId);
//        Optional<Product> product = Optional.ofNullable(productRepository.findById(productId).orElseThrow(() -> new ProductNotFound(String.format("Product with ID %s not found", productId))));
        Product product = productRepository.findById(String.valueOf(prodId)).orElseThrow(() -> new ProductNotFound(String.format("Product with ID %s not found", productId)));
        productRepository.delete(product);
        return String.format("Product with ID %s has been successfully deleted", productId);
    }

    @Override
    public ProductResponse productUpdate(String productId, ProductRequest request) {
       return Optional.ofNullable(productRepository.findById(productId).orElseThrow(() -> new ProductNotFound(String.format("Product with ID %s not found", productId)))).stream().map(i -> mapToProduct(request, i)).findFirst().map(MapperClass::mapToProductResponse).stream().findFirst().get();
    }

    @Override
    public ProductResponse addProduct(ProductRequest request) {
        Product product = productRepository.save(mapToProduct(request, new Product()));
        return mapToProductResponse(product);
    }
}