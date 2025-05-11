package com.thm.ecommerce.orderline;

public record OrderLineResponse(
        Integer orderId,
        double quantity
) {
}
