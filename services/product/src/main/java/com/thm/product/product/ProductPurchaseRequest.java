package com.thm.product.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message= "ProductId is mandatory")
        Integer productId,
        @NotNull(message= "Quantity is mandatory")
        double quantity
) {
}
