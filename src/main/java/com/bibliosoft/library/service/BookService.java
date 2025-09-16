package com.bibliosoft.library.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliosoft.library.entity.BookEntity;
import com.bibliosoft.library.entity.UserEntity;
import com.bibliosoft.library.repository.AuthorRepository;
import com.bibliosoft.library.repository.BookRepository;
import com.bibliosoft.library.repository.UserRepository;
import com.bibliosoft.library.request.AddBookRequest;

/**
 * Servicio que gestiona las operaciones de negocio relacionadas con los libros,
 * ahora usando JPA y H2 como almacenamiento persistente.
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Retorna todos los libros disponibles en el sistema.
     *
     * @return Lista de libros.
     */
    public List<BookEntity> getAll() {
        return bookRepository.findAll();
    }

    /**
     * Registra un nuevo libro, asignándole un ID automáticamente.
     *
     * @param book DTO del libro a crear.
     * @return Libro creado con ID asignado.
     */
    public BookEntity add(AddBookRequest book) {
        BookEntity bookEntity = BookEntity.builder()
                .title(book.title())
                .author(authorRepository.findById(book
                        .authorId()).orElseThrow())
                .build();
        return bookRepository.save(bookEntity);
    }

    /**
     * Obtiene un libro por su identificador.
     *
     * @param id ID del libro.
     * @return Optional con el libro encontrado, o vacío si no existe.
     */
    public Optional<BookEntity> getById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Elimina un libro por su ID.
     *
     * @param id ID del libro a eliminar.
     * @return true si el libro existía y fue eliminado, false si no existía.
     */
    public boolean delete(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Marca un libro como prestado a un usuario si está disponible.
     *
     * @param bookId ID del libro
     * @param userId ID del usuario que solicita el préstamo
     * @return Libro actualizado
     * @throws NoSuchElementException si el libro o el usuario no existen
     * @throws IllegalStateException  si el libro ya está prestado
     */
    public BookEntity borrowBook(Long bookId, Long userId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found with ID: " + bookId));

        if (book.isBorrowed()) {
            throw new IllegalStateException("Book is already borrowed.");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        book.setBorrowed(true);
        book.setBorrowedBy(user);

        return bookRepository.save(book);
    }
}
