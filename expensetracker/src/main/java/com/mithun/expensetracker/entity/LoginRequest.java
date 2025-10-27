package com.mithun.expensetracker.entity;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String userName;
    private String password;
}
