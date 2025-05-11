package com.thm.ecommerce.order;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(@Valid OrderRequest orderRequest) {
        return Order.builder()
                .id(orderRequest.id())
                .customerId(orderRequest.customerId())
                .paymentMethod(orderRequest.paymentMethod())
                .reference(orderRequest.reference())
                .totalAmount(orderRequest.amount())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
