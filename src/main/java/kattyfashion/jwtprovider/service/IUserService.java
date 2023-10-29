package kattyfashion.jwtprovider.service;

import kattyfashion.jwtprovider.model.User;
import kattyfashion.jwtprovider.model.dtos.user.UserDTOIn;

import java.util.Optional;


public interface IUserService{

    Optional<User> getById(Long id);

    Optional<User> getUserByUsername(String username);

    Optional<User> createUser(UserDTOIn user);

    Optional<User> getUserByEmail(String email);


}
