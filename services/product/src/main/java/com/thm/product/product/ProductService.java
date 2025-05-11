package com.thm.product.product;

import com.thm.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public Integer createProduct(@Valid ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);
        return productRepository.save(product).getId();

    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> requestList) {
        var productIds = requestList
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if(storedProducts.size() != productIds.size()){
            throw new ProductPurchaseException("One or more products does not exists");
        }

        var storedRequest = requestList
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i = 0; i< storedRequest.size(); i++){
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Product with id " + productRequest.productId() + " does not have enough quantity");
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(productMapper.toProductPurchaseResponse(product, productRequest.quantity()));


        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " was not found" ));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
