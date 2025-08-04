package com.bibliosoft.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliosoft.library.entity.AuthorEntity;

/**
 * Repositorio de la entidad AuthorEntity.
 */
@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    /**
     * Busca un autor por su nombre.
     *
     * @param name Nombre del autor.
     * @return AuthorEntity si existe, null si no.
     */
    boolean existsByName(String name);
}