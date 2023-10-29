package kattyfashion.jwtprovider.service;

import kattyfashion.jwtprovider.model.Role;
import kattyfashion.jwtprovider.model.RoleEnum;

import java.util.Optional;

public interface IRoleService {

    Optional<Role> getRole(RoleEnum role);
}
