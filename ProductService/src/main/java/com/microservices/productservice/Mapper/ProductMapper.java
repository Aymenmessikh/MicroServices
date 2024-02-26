package com.microservices.productservice.Mapper;

import com.microservices.productservice.Dto.ProductRequest;
import com.microservices.productservice.Dto.ProductResponse;
import com.microservices.productservice.Entity.Product;

public interface ProductMapper {
    public static Product DtoToEntity(ProductRequest productRequest){
        return Product.builder()
                .description(productRequest.getDescription())
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .build();
    }
    public static ProductResponse DtoFromEntity(Product product){
        return ProductResponse.builder()
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
