package kattyfashion.jwtprovider.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RoleEnum {
    ADMIN , USER;

    public static RoleEnum getRole(String role) {
        return switch (role) {
            case "admin" -> RoleEnum.ADMIN;
            case "user" -> RoleEnum.USER;
            default -> null;
        };
    }

}

