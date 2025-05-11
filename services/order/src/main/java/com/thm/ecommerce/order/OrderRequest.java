package com.thm.ecommerce.order;


import com.thm.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order Amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment Method should be precised")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer Id should be present")
        @NotEmpty(message = "Customer Id should be present")
        @NotBlank(message = "Customer Id should be present")
        String customerId,
        @NotEmpty(message = "You schould at least purchase one product")
        List<PurchaseRequest> products


) {
}
