package com.microservices.productservice.Controller;

import com.microservices.productservice.Dto.ProductRequest;
import com.microservices.productservice.Dto.ProductResponse;
import com.microservices.productservice.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/product")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<ProductRequest> addProduct(@RequestBody ProductRequest productRequest){
        ProductRequest productRequest1=productService.addProdcut(productRequest);
        return new ResponseEntity<>(productRequest1, HttpStatus.CREATED);
    }
    @GetMapping("findAll")
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        List<ProductResponse> productResponses=productService.getAllProduct();
        return new ResponseEntity<>(productResponses,HttpStatus.OK);
    }
}
