package com.thm.ecommerce.kafka.payment;

import com.thm.ecommerce.kafka.order.Customer;
import com.thm.ecommerce.kafka.order.Product;

import java.math.BigDecimal;
import java.util.List;

public record PaymentConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        String id,
        String customerFirstname,
        String customerLastname,
        String customerEmail

) {
}
