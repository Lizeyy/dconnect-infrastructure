package com.dconnect.infrastructure.repository;

import com.dconnect.infrastructure.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByTokenAndActiveIsTrue(String token);
}
