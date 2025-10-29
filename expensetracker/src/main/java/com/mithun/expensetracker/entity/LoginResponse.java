package com.mithun.expensetracker.entity;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private Long userId;
    private Integer statusCode;
}
