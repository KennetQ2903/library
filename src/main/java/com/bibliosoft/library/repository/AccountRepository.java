package com.bibliosoft.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliosoft.library.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    /**
     * Verifica si existe una cuenta con el nombre de usuario dado.
     * @param username
     * @return
     */
    boolean existsByUsername(String username);

    /**
     * Busca una cuenta por su nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return Cuenta si existe; null si no.
     */
    Optional<AccountEntity> findByUsername(String username);
}
