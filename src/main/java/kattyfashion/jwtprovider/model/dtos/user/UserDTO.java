package kattyfashion.jwtprovider.model.dtos.user;

import kattyfashion.jwtprovider.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserDTO {
    String username;
    String password;

    public UserDTO(User user) {

    this.username = user.getUsername();
    this.password = user.getPassword();
    }

    public UserDTO(String username) {
        this.username = username;
    }
}
