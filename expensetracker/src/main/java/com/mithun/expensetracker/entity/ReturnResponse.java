package com.mithun.expensetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnResponse {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal remainingAmount;
}
