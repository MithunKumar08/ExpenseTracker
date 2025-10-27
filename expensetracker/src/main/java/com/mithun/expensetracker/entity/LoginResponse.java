package com.mithun.expensetracker.entity;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private Long userId;
    private String statusCode;
}
