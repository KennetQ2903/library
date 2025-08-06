package com.bibliosoft.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliosoft.library.entity.AccountEntity;
import com.bibliosoft.library.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Obtiene la lista completa de cuentas registradas.
     * @return
     */
    public List<AccountEntity> getAll() {
        return accountRepository.findAll();
    }

    /**
     * Registra una nueva cuenta. El ID se genera automáticamente.
     * @param account
     * @return AccountEntity
     */
    public AccountEntity add(AccountEntity account) {
        return accountRepository.save(account);
    }

    /**
     * Obtiene una cuenta por su ID.
     * @param id
     * @return
     */
    public Optional<AccountEntity> getById(Long id) {
        return accountRepository.findById(id);
    }

    /**
     * Elimina una cuenta por su ID.
     * @param id
     * @return true si la cuenta fue eliminada; false si no existía.
     */
    public boolean delete(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
