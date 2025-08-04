package com.bibliosoft.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliosoft.library.entity.BookEntity;

/*
 * Repositorio de la entidad BookEntity.
 */
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    /**
     * Busca todos los libros de un autor.
     *
     * @param authorId ID del autor.
     * @return Lista de BookEntity.
     */
    List<BookEntity> findByAuthorId(Long authorId);

    /**
     * Busca todos los libros prestados por un usuario.
     *
     * @param userId ID del usuario.
     * @return Lista de BookEntity.
     */
    List<BookEntity> findByBorrowed(boolean borrowed);

    /**
     * Busca todos los libros prestados por un usuario.
     *
     * @param userId ID del usuario.
     * @return Lista de BookEntity.
     */
    List<BookEntity> findByBorrowedById(Long userId);
}
