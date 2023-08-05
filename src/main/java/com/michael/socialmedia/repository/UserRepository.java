package com.michael.socialmedia.repository;

import com.michael.socialmedia.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByEmailAndId(String email,Long id);
    Optional<User> findByEmail(String email);
}
