package com.michael.socialmedia.repository;

import com.michael.socialmedia.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {

    User existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
