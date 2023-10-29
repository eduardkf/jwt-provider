package kattyfashion.jwtprovider.model.dtos.user;

import kattyfashion.jwtprovider.model.Role;
import kattyfashion.jwtprovider.model.RoleEnum;
import kattyfashion.jwtprovider.model.User;
import kattyfashion.jwtprovider.service.RoleServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UserDTOIn {

    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String email;
    public String role;

    public User createUser(RoleServiceImpl roleService) {
        User newUser = new User();
        newUser.setUsername(this.username);
        newUser.setPassword(this.password);
        newUser.setFirstName(this.firstName);
        newUser.setLastName(this.lastName);
        newUser.setEmail(this.email);

        if(role != null) {
            Optional<Role> retrievedRole = roleService.getRole(RoleEnum.valueOf(role));

            retrievedRole.ifPresentOrElse(
                    role1 -> newUser.setRoles(Set.of(retrievedRole.get())),
                    () -> {
                        Role newRole = new Role();
                        newRole.setRole(RoleEnum.valueOf(role));
                        newUser.setRoles(Set.of(newRole));
                    }
            );

        } else {
            Role newDefaultRole = new Role();
            newDefaultRole.setRole(RoleEnum.USER);
            newUser.setRoles(Set.of(newDefaultRole));
        }
       return newUser;
    }
}
