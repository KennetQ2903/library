package com.bibliosoft.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliosoft.library.entity.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    /**
     * Busca todos los tokens de un usuario.
     *
     * @param accountId ID del usuario.
     * @return Lista de TokenEntity.
     */
    List<TokenEntity> findAllValidIsFalseOrRevokedIsFalseByAccountId(Long accountId);

    /**
     * Busca un token por su token.
     *
     * @param token Token.
     * @return TokenEntity si existe; null si no.
     */
    Optional<TokenEntity> findByToken(String token);
}
