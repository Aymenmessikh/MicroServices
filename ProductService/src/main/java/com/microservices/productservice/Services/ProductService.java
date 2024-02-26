package com.microservices.productservice.Services;

import com.microservices.productservice.Dto.ProductRequest;
import com.microservices.productservice.Dto.ProductResponse;
import com.microservices.productservice.Entity.Product;
import com.microservices.productservice.Mapper.ProductMapper;
import com.microservices.productservice.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    public ProductRequest addProdcut(ProductRequest productRequest){
         productRepository.save(ProductMapper.DtoToEntity(productRequest));
        return productRequest;
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> products=productRepository.findAll();
        return products.stream().map(ProductMapper::DtoFromEntity).collect(Collectors.toList());
    }
}
