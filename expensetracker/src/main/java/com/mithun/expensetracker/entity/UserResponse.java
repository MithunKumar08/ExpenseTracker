package com.mithun.expensetracker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long userId;
    @JsonProperty("username")
    private String userName;
    private String email;
    @DecimalMin(value = "0.00", message = "Total Amount must be greater than zero")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal totalAmount;
}
