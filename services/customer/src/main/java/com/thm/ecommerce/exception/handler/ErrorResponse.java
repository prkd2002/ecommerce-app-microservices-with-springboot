package com.thm.ecommerce.exception.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> error
) {

}
