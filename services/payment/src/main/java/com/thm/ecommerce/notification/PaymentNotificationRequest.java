package com.thm.ecommerce.notification;

import com.thm.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customFirstname,
        String customLastname,
        String customEmail

) {
}
