package com.hamitmizrak.security.jwt;

import lombok.*;

// LOMBOK
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class _2_RegisterRequest {

    // Field
    private String username;
    private String password;
}
