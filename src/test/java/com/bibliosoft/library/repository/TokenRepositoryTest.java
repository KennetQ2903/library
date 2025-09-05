package com.bibliosoft.library.repository;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bibliosoft.library.entity.AccountEntity;
import com.bibliosoft.library.entity.TokenEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveAndFindById() {
        AccountEntity account = new AccountEntity();
        account.setUsername("juan123");
        account.setPassword("password123");
        account = accountRepository.save(account);

        TokenEntity token = new TokenEntity();
        token.setToken("jwtToken");
        token.setAccount(account);
        token.setRevoked(false);
        token.setExpired(false);

        TokenEntity saved = tokenRepository.save(token);

        TokenEntity found = tokenRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("jwtToken", found.getToken());
        assertEquals(account.getId(), found.getAccount().getId());
    }

    @Test
    public void testFindAllValidIsFalseOrRevokedIsFalseByAccountId() {
        AccountEntity account = new AccountEntity();
        account.setUsername("ana123");
        account.setPassword("pass1234");
        account = accountRepository.save(account);

        TokenEntity token1 = new TokenEntity(null, "token1", TokenEntity.TokenType.BEARER, false, false, account);
        TokenEntity token2 = new TokenEntity(null, "token2", TokenEntity.TokenType.BEARER, false, true, account); // expired
        TokenEntity token3 = new TokenEntity(null, "token3", TokenEntity.TokenType.BEARER, true, false, account); // revoked
        tokenRepository.save(token1);
        tokenRepository.save(token2);
        tokenRepository.save(token3);

        List<TokenEntity> tokens = tokenRepository.findAllValidIsFalseOrRevokedIsFalseByAccountId(account.getId());
        // Según el nombre, debería devolver tokens donde revoked=false o expired=false
        assertEquals(3, tokens.size());
        List<String> tokenNames = new ArrayList<>();
        tokens.forEach(t -> tokenNames.add(t.getToken()));
        assertTrue(tokenNames.contains("token1"));
        assertTrue(tokenNames.contains("token2"));
    }

    @Test
    public void testFindByToken() {
        AccountEntity account = new AccountEntity();
        account.setUsername("luis123");
        account.setPassword("pass5678");
        account = accountRepository.save(account);

        TokenEntity token = new TokenEntity();
        token.setToken("specialToken");
        token.setAccount(account);
        token.setExpired(false);
        token.setRevoked(false);

        tokenRepository.save(token);

        Optional<TokenEntity> found = tokenRepository.findByToken("specialToken");
        assertTrue(found.isPresent());
        assertEquals("specialToken", found.get().getToken());
        assertEquals(account.getId(), found.get().getAccount().getId());
    }

    @Test
    public void testDeleteToken() {
        AccountEntity account = new AccountEntity();
        account.setUsername("maria123");
        account.setPassword("pass9999");
        account = accountRepository.save(account);

        TokenEntity token = new TokenEntity(null, "deleteToken", TokenEntity.TokenType.BEARER, false, false, account);
        token = tokenRepository.save(token);

        tokenRepository.deleteById(token.getId());
        assertFalse(tokenRepository.findById(token.getId()).isPresent());
    }
}
