package kattyfashion.jwtprovider.repository;

import kattyfashion.jwtprovider.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

     Optional<User> findByUsername(String username);

     Optional<User> findByEmail(String email);

     Optional<User> getById(Long id);

}
