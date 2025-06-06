package com.fiap.api.dto;

import lombok.Data;

@Data
public class LimitRequestDTO {
    private Integer limit = 10; // Default value of 10 if not specified

    public Integer getValidatedLimit() {
        if (limit == null || limit <= 0) {
            return 10; // Default value if null or invalid
        }
        return Math.min(limit, 100); // Maximum of 100 records
    }
} 