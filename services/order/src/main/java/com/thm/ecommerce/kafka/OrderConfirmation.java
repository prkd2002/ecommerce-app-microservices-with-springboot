package com.thm.ecommerce.kafka;

import com.thm.ecommerce.customer.CustomerResponse;
import com.thm.ecommerce.order.PaymentMethod;
import com.thm.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
