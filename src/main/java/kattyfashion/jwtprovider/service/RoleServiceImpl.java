package kattyfashion.jwtprovider.service;

import kattyfashion.jwtprovider.model.Role;
import kattyfashion.jwtprovider.model.RoleEnum;
import kattyfashion.jwtprovider.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<Role> getRole(RoleEnum role) {
        return roleRepository.findRoleByRole(role);
    }
}
