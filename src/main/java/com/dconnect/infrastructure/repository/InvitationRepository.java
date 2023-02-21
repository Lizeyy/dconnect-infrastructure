package com.dconnect.infrastructure.repository;

import com.dconnect.infrastructure.domain.Invitation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvitationRepository extends MongoRepository<Invitation, String> {

}
