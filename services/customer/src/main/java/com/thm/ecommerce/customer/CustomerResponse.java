package com.thm.ecommerce.customer;

import jakarta.validation.constraints.NotNull;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address

) {
}
