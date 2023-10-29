package kattyfashion.jwtprovider.service;

import jakarta.persistence.EntityNotFoundException;
import kattyfashion.jwtprovider.errors.ErrorMessage;
import kattyfashion.jwtprovider.errors.KfError;
import kattyfashion.jwtprovider.model.User;
import kattyfashion.jwtprovider.model.dtos.user.UserDTOIn;
import kattyfashion.jwtprovider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository ;

    @Autowired
    RoleServiceImpl roleService;

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> createUser(UserDTOIn user) {
        if (getUserByUsername(user.getUsername()).isEmpty() && getUserByEmail(user.getEmail()).isEmpty()) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            return Optional.of(userRepository.save(user.createUser(
                roleService
            )));
        }
        throw new KfError(ErrorMessage.E_01);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        }
        catch (EntityNotFoundException e) {
            throw new KfError(ErrorMessage.E_02);
        }
    }
}
