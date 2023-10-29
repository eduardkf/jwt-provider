package kattyfashion.jwtprovider.errors;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    E_01("User already exists!"),
    E_02("User not found!"),
    E_03("Invalid JWT token!"),
    E_04("JWT token is expired"),
    E_05("JWT token is unsupported"),
    E_06("JWT claims string is empty"),
    E_07("Cannot set user authentication"),
    E_08("JWT not in Redis");

    String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
