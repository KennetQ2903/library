package com.bibliosoft.library.repository;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bibliosoft.library.entity.AccountEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveAndFindById() {
        AccountEntity account = new AccountEntity();
        account.setUsername("juan123");
        account.setPassword("password123");

        AccountEntity saved = accountRepository.save(account);

        Optional<AccountEntity> found = accountRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("juan123", found.get().getUsername());
    }

    @Test
    public void testExistsByUsername() {
        AccountEntity account = new AccountEntity();
        account.setUsername("ana456");
        account.setPassword("password456");

        accountRepository.save(account);

        assertTrue(accountRepository.existsByUsername("ana456"));
        assertFalse(accountRepository.existsByUsername("noexiste"));
    }

    @Test
    public void testFindByUsername() {
        AccountEntity account = new AccountEntity();
        account.setUsername("carlos789");
        account.setPassword("password789");

        accountRepository.save(account);

        Optional<AccountEntity> found = accountRepository.findByUsername("carlos789");
        assertTrue(found.isPresent());
        assertEquals("carlos789", found.get().getUsername());

        Optional<AccountEntity> notFound = accountRepository.findByUsername("desconocido");
        assertFalse(notFound.isPresent());
    }

    @Test
    public void testDeleteAccount() {
        AccountEntity account = new AccountEntity();
        account.setUsername("eliminar1");
        account.setPassword("password");

        AccountEntity saved = accountRepository.save(account);
        Long id = saved.getId();

        accountRepository.deleteById(id);

        Optional<AccountEntity> found = accountRepository.findById(id);
        assertFalse(found.isPresent());
    }
}
