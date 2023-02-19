package com.dconnect.infrastructure.service;

import com.dconnect.infrastructure.domain.Connection;
import com.dconnect.infrastructure.domain.Token;
import com.dconnect.infrastructure.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public String createToken(Connection connection) {
        final Token token = new Token();
        token.setCreationDate(OffsetDateTime.now());
        token.setConnection(connection);
        token.setActive(true);
        token.setToken(RandomStringUtils.random(60, true, true));

        return tokenRepository.save(token).getToken();
    }

}
