package com.dconnect.infrastructure.service;

import com.dconnect.infrastructure.domain.Connection;
import com.dconnect.infrastructure.domain.Token;
import com.dconnect.infrastructure.error.TokenNotActive;
import com.dconnect.infrastructure.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

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

    public boolean checkIfTokenExist(String token) {
        return tokenRepository.findByTokenAndActiveIsTrue(token).isPresent();
    }

    public Connection getConnectionByToken(String tokenString) {
        final Optional<Token> token = tokenRepository.findByTokenAndActiveIsTrue(tokenString);

        if (token.isPresent()) {
            return token.get().getConnection();
        } else {
            throw new TokenNotActive("Podany token nie pasuje do żadnego połączenia lub jest nieaktywny");
        }
    }
}
