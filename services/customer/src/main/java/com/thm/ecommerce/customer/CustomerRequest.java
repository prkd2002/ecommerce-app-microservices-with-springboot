package com.thm.ecommerce.customer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record    CustomerRequest(
            String id,
         @NotNull(message = "Customer firstname is required")
         String firstName,
         @NotNull(message = "Customer lastname is required")
         String lastName,
         @NotNull(message = "Customer email is required")
         String email,
        @NotNull(message = "Customer address is required")
      Address address
) {


}
