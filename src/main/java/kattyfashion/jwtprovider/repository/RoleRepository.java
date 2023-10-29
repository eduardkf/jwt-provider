package kattyfashion.jwtprovider.repository;

import kattyfashion.jwtprovider.model.Role;
import kattyfashion.jwtprovider.model.RoleEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findRoleByRole(RoleEnum role);
}
