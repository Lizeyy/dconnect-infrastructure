package com.dconnect.infrastructure.repository;

import com.dconnect.infrastructure.domain.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {


}
