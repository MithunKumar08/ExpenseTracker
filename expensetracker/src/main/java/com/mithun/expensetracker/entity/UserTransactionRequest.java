package com.mithun.expensetracker.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionRequest {

        @Pattern(regexp = "income|expense", message = "Type must be either 'income' or 'expense'")
        private String type;
        @NotEmpty(message = "Category cannot be empty")
        private String category;
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        @Digits(integer = 7, fraction = 2)
        private BigDecimal amount;
        private String description;
        private LocalDateTime date;
}
