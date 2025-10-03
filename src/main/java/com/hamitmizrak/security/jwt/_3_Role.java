package com.hamitmizrak.security.jwt;

import lombok.Getter;

// LOMBOK
@Getter
public enum _3_Role {

    // Enum Value
    USER(1,"user"),WRITER(2,"writer"), ADMIN(3,"admin");

    //Field
    private final int ket;
    private final String value;

    // Parametreli Constructor
    private _3_Role(int ket, String value) {
        this.ket = ket;
        this.value = value;
    }
} //end _3_Role
