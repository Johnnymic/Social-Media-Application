package com.michael.socialmedia.repository;

import com.michael.socialmedia.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {

    @Query( "SELECT t FROM Token t INNER JOIN User u ON t.user.id = u.id WHERE  u.id =:userId AND (t.isRevoked= FAlSE  OR t.isExpired =FALSE)")
    List<Token>findAllByValidToken(Long userId);


    Optional<Token> findByToken(String token);
}
