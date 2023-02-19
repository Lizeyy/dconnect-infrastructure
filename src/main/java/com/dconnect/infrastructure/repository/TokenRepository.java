package com.dconnect.infrastructure.repository;

import com.dconnect.infrastructure.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TokenRepository extends JpaRepository<Token, Long> {

}
