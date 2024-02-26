package com.microsevices.orderservice.Services;

import brave.Span;
import brave.Tracer;
import com.microsevices.orderservice.Dto.InventoryResponse;
import com.microsevices.orderservice.Dto.OrderLigneItemsDto;
import com.microsevices.orderservice.Dto.OrderRequest;
import com.microsevices.orderservice.Enitiy.OrderLigneItems;
import com.microsevices.orderservice.Events.OrderPlacedEvents;
import com.microsevices.orderservice.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.microsevices.orderservice.Enitiy.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import zipkin2.internal.Trace;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    private final Tracer tracer;
    private final KafkaTemplate<String,OrderPlacedEvents> kafkaTemplate;
    public InventoryResponse[] addOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLigneItems> orderLigneItems =
                orderRequest.getOrderLigneItemsDtos().stream().map(this::DtoTOEnity).toList();
        order.setOrderLigneItems(orderLigneItems);

        List<String> skuCodes = order.getOrderLigneItems().stream()
                .map(OrderLigneItems::getSkuCode)
                .toList();
        // Appel du service d'inventaire pour vérifier si les produits sont en stock
        Span inventoryServiceLoukup = tracer.nextSpan().name("inventoryServiceLoukup");
        try (Tracer.SpanInScope spanInScope = tracer.withSpanInScope(inventoryServiceLoukup.start())) {
            InventoryResponse[] inventoryResponses = webClient.build().get()
                    .uri("http://Inventory-Service/api/inventory/get", uriBuilder ->
                            uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            // Vérification si tous les produits sont en stock
            //   boolean allProductIsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

            // Si tous les produits sont en stock, enregistrer la commande
            if (Arrays.stream(inventoryResponses).allMatch(inventoryResponse -> inventoryResponse.isInStock())
                    && !Arrays.stream(inventoryResponses).allMatch(inventoryResponse -> skuCodes.isEmpty())) {
                kafkaTemplate.send("Not",new OrderPlacedEvents(order.getOrderNumber()));
                log.info("message Envoyer: "+order.getOrderNumber());
                orderRepository.save(order);
            } else {
                throw new IllegalArgumentException("Certains produits ne sont pas disponibles en stock");
            }

            return inventoryResponses;
        }finally {
            inventoryServiceLoukup.finish();
        }
    }

    public OrderLigneItems DtoTOEnity(OrderLigneItemsDto orderLigneItemsDto){
        return OrderLigneItems.builder()
                .price(orderLigneItemsDto.getPrice())
                .skuCode(orderLigneItemsDto.getSkuCode())
                .quantity(orderLigneItemsDto.getQuantity())
                .build();
    }
}
