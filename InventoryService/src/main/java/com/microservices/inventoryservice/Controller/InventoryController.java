package com.microservices.inventoryservice.Controller;

import com.microservices.inventoryservice.Dto.InventoryResponse;
import com.microservices.inventoryservice.Services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping("/get")
    public ResponseEntity<List<InventoryResponse>> inInStock(@RequestParam List<String> skuCode){
        List<InventoryResponse> isInStock=inventoryService.isInStock(skuCode);
        return new ResponseEntity<>(isInStock, HttpStatus.OK);
    }
}
