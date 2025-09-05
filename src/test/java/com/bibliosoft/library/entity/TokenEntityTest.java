package com.bibliosoft.library.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TokenEntityTest {
    @Test
    public void testTokenEntity_BuilderAndGetters() {
        AccountEntity account = new AccountEntity();
        account.setId(1L);

        TokenEntity token = TokenEntity.builder()
                .id(100L)
                .token("jwtToken123")
                .tokenType(TokenEntity.TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .account(account)
                .build();

        assertEquals(Long.valueOf(100L), token.getId());
        assertEquals("jwtToken123", token.getToken());
        assertEquals(TokenEntity.TokenType.BEARER, token.getTokenType());
        assertFalse(token.isRevoked());
        assertFalse(token.isExpired());
        assertEquals(account, token.getAccount());

        // Test setters
        token.setRevoked(true);
        token.setExpired(true);
        token.setToken("newToken456");
        assertTrue(token.isRevoked());
        assertTrue(token.isExpired());
        assertEquals("newToken456", token.getToken());
    }
}
