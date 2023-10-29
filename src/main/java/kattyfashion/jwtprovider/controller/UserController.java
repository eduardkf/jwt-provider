package kattyfashion.jwtprovider.controller;

import kattyfashion.jwtprovider.config.jwt.JwtResponse;
import kattyfashion.jwtprovider.config.jwt.JwtUtils;
import kattyfashion.jwtprovider.model.User;
import kattyfashion.jwtprovider.model.dtos.user.UserDTO;
import kattyfashion.jwtprovider.model.dtos.user.UserDTOIn;
import kattyfashion.jwtprovider.service.CacheService;
import kattyfashion.jwtprovider.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final CacheService cacheService;

    public UserController ( CacheService cacheService, UserServiceImpl userServiceImpl, AuthenticationManager authenticationManager) {
        this.userServiceImpl = userServiceImpl;
        this.authenticationManager = authenticationManager;
        this.cacheService = cacheService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserDTOIn userDTOIn) {
        //Authenticate logic
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTOIn.getUsername(), userDTOIn.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<String> jwt = cacheService.getCachedToken(authentication.getName());

        kattyfashion.jwtprovider.model.User newUser = userServiceImpl.getUserByUsername(userDTOIn.getUsername()).orElseThrow();
        List<String> roles = newUser.getRoles().stream()
                .map(role -> role.getRole().toString())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new JwtResponse(jwt.get(0),
                jwt.get(1),
                newUser.getId(),
                newUser.getUsername(),
                newUser.getEmail(),
                roles));
    }

//    @PostMapping("/logout")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<String> logout(@RequestBody UserDTOIn userDTOIn) {
////        cacheService.clearCache(token);
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Logged out successfully");
//    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserDTOIn userDTOIn) {

        if (userDTOIn.getUsername() != null && userDTOIn.getPassword() != null) {
        userServiceImpl.createUser(userDTOIn).orElseThrow();
        }

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("You have successfully registered!");
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> testUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userServiceImpl.getUserByUsername( userDetails.getUsername()).orElseThrow();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserDTO(user.getUsername()));

    }

}
