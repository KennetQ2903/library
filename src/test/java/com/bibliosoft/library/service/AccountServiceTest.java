package com.bibliosoft.library.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bibliosoft.library.entity.AccountEntity;
import com.bibliosoft.library.repository.AccountRepository;

public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddAccount() {
        AccountEntity account = new AccountEntity(null, "juan123", "Test123!", null);
        AccountEntity savedAccount = new AccountEntity(1L, "juan123", "Test123!", null);

        when(accountRepository.save(any(AccountEntity.class))).thenReturn(savedAccount);

        AccountEntity result = accountService.add(account);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals("juan123", result.getUsername());
    }

    @Test
    public void testGetAllAccounts() {
        List<AccountEntity> accounts = Arrays.asList(
                new AccountEntity(1L, "ana456", "Test123!", null),
                new AccountEntity(2L, "luis789", "Test123!", null));

        when(accountRepository.findAll()).thenReturn(accounts);

        List<AccountEntity> result = accountService.getAll();

        assertEquals(2, result.size());
        assertEquals("ana456", result.get(0).getUsername());
    }

    @Test
    public void testGetAccountById() {
        AccountEntity account = new AccountEntity(1L, "carlos123", "Test123!", null);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Optional<AccountEntity> result = accountService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("carlos123", result.get().getUsername());
    }

    @Test
    public void testGetAccountById_NotFound() {
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<AccountEntity> result = accountService.getById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteAccount() {
        Long accountId = 1L;

        when(accountRepository.existsById(accountId)).thenReturn(true);
        doNothing().when(accountRepository).deleteById(accountId);

        boolean result = accountService.delete(accountId);

        assertTrue(result);
        verify(accountRepository).deleteById(accountId);
    }

    @Test
    public void testDeleteAccount_NotFound() {
        when(accountRepository.existsById(12345L)).thenReturn(false);

        boolean result = accountService.delete(12345L);

        assertFalse(result);
        verify(accountRepository, never()).deleteById(anyLong());
    }
}
