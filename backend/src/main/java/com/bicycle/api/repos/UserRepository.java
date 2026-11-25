package com.bicycle.api.repos;

import com.bicycle.api.entities.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsernameAndPassword(String username, String password);
}
