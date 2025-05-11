package com.thm.ecommerce.payment;


import com.thm.ecommerce.customer.CustomerResponse;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        com.thm.ecommerce.order.@jakarta.validation.constraints.NotNull(message = "Payment Method should be precised") PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
