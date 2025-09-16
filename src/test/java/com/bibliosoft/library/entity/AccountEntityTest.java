package com.bibliosoft.library.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AccountEntityTest {
    @Test
    public void testAccountEntity_BuilderAndGetters() {
        AccountEntity account = AccountEntity.builder()
                .id(1L)
                .username("juan123")
                .password("password123")
                .tokens(new ArrayList<>())
                .build();

        assertEquals(Long.valueOf(1L), account.getId());
        assertEquals("juan123", account.getUsername());
        assertEquals("password123", account.getPassword());
        assertNotNull(account.getTokens());
        assertTrue(account.getTokens().isEmpty());

        // Test setters
        account.setUsername("maria456");
        account.setPassword("pass45678");
        assertEquals("maria456", account.getUsername());
        assertEquals("pass45678", account.getPassword());
    }

    @Test
    public void testAccountEntity_WithTokens() {
        AccountEntity account = new AccountEntity();
        account.setId(1L);
        account.setUsername("user1");
        account.setPassword("password1");

        TokenEntity token1 = new TokenEntity();
        token1.setId(10L);
        token1.setToken("token1");
        token1.setAccount(account);

        TokenEntity token2 = new TokenEntity();
        token2.setId(11L);
        token2.setToken("token2");
        token2.setAccount(account);

        List<TokenEntity> tokens = new ArrayList<>();
        tokens.add(token1);
        tokens.add(token2);

        account.setTokens(tokens);

        assertEquals(2, account.getTokens().size());
        assertEquals("token1", account.getTokens().get(0).getToken());
        assertEquals("token2", account.getTokens().get(1).getToken());
        assertEquals(account, account.getTokens().get(0).getAccount());
    }
}
