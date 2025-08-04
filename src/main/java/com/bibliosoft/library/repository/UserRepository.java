package com.bibliosoft.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliosoft.library.entity.UserEntity;

/**
 * Repositorio de la entidad UserEntity.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Busca un usuario por su nombre.
     *
     * @param name Nombre del usuario.
     * @return UserEntity si existe, null si no.
     */
    boolean existsByName(String name);
}