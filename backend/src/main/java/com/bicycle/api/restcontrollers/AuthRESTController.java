package com.bicycle.api.restcontrollers;

import com.bicycle.api.entities.AppUser;
import com.bicycle.api.repos.UserRepository;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthRESTController {
    private final UserRepository userRepository;

    public AuthRESTController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AppUser credentials) {
        AppUser user = userRepository
                .findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Nom d'utilisateur ou mot de passe incorrecte"));

        return Map.of(
                "username", user.getUsername(),
                "roles", new String[] { user.getRole() }
        );
    }
}
