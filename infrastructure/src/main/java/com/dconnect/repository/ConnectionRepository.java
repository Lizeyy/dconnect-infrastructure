package com.dconnect.infrastructureservice.repository;

import com.dconnect.infrastructureservice.domain.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
}
