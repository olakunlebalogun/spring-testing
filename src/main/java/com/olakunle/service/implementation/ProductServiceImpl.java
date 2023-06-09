package com.olakunle.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.olakunle.dto.request.ProductRequest;
import com.olakunle.dto.response.ProductResponse;
import com.olakunle.exception.ProductNotFoundException;
import com.olakunle.model.Product;
import com.olakunle.repository.ProductRepository;
import com.olakunle.service.ProductService;
import com.olakunle.utils.MapperClass;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import static com.olakunle.utils.MapperClass.*;


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

//        Optional<Product> product = Optional.ofNullable(productRepository.findById(productId).orElseThrow(() -> new ProductNotFound(String.format("Product with ID %s not found", productId))));
        Product product = productRepository.findById(Long.valueOf(productId)).orElseThrow(() -> new ProductNotFoundException(String.format("Product with ID %s not found", productId)));
        productRepository.delete(product);
        return String.format("Product with ID %s has been successfully deleted", productId);
    }

    @Override
    public ProductResponse productUpdate(String productId, ProductRequest request) {
//       return Optional.ofNullable(productRepository.findById(productId).orElseThrow(() -> new ProductNotFound(String
//                       .format("Product with ID %s not found", productId))))
//                       .stream().map(i -> mapToProduct(request, i)).findFirst()
//                       .isPresent()
//               map(MapperClass::mapToProductResponse).stream().findFirst().get();
        Product fetchedProduct = productRepository.findById(Long.valueOf(productId)).orElseThrow(() -> new ProductNotFoundException(String.format("Product with ID %s not found", productId)));
        Product updatedProduct = mapToProduct(request, fetchedProduct);
        productRepository.save(updatedProduct);
        return mapToProductResponse(updatedProduct);
    }

    @Override
    public ProductResponse addProduct(ProductRequest request) {
        Product product = productRepository.save(mapToProduct(request, new Product()));
        return mapToProductResponse(product);
    }

    @Override
    public ProductResponse getSingleProduct(String id) {
        return Optional.of(productRepository
                .findById(Long.valueOf(id)).orElseThrow(() -> new ProductNotFoundException(String.format("Product with ID %s not found", id))))
                .stream().map(MapperClass::mapToProductResponse)
                .findFirst().get();
    }

    @Override
    public ResponseEntity<ProductResponse> updateCustomer(String id, JsonPatch patch) {
        try {
            Product product = productRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ProductNotFoundException(String.format("Product with ID %s not found", id)));
            Product patchedProduct = applyPatchToProduct(patch, product);
            productRepository.save(patchedProduct);
            return ResponseEntity.ok(mapToProductResponse(patchedProduct));
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
