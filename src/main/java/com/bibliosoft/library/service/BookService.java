package com.bibliosoft.library.service;

import com.bibliosoft.library.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Servicio que gestiona las operaciones de negocio relacionadas con los libros.
 * Utiliza un almacenamiento en memoria simulado con un Map.
 */
@Service
public class BookService {

    /**
     * Almacenamiento en memoria de libros. 
     * Clave: ID del libro, Valor: DTO del libro.
     */
    private final Map<Long, BookDTO> bookRepository = new HashMap<>();

    /**
     * Contador de secuencia para asignar IDs únicos a los libros.
     */
    private Long idSequence = 1L;

    /**
     * Retorna todos los libros disponibles en el sistema.
     *
     * @return Lista de BookDTO.
     */
    public List<BookDTO> getAll() {
        return new ArrayList<>(bookRepository.values());
    }

    /**
     * Registra un nuevo libro, asignándole un ID automáticamente.
     *
     * @param book DTO con los datos del libro.
     * @return DTO del libro creado, incluyendo el ID.
     */
    public BookDTO add(BookDTO book) {
        book.setId(idSequence++);
        bookRepository.put(book.getId(), book);
        return book;
    }

    /**
     * Obtiene un libro por su identificador.
     *
     * @param id ID del libro.
     * @return Optional con el libro encontrado, o vacío si no existe.
     */
    public Optional<BookDTO> getById(Long id) {
        return Optional.ofNullable(bookRepository.get(id));
    }

    /**
     * Elimina un libro por su ID.
     *
     * @param id ID del libro a eliminar.
     * @return true si el libro existía y fue eliminado, false si no existía.
     */
    public boolean delete(Long id) {
        return bookRepository.remove(id) != null;
    }

    /**
     * Marca un libro como prestado por un usuario, si está disponible.
     *
     * @param bookId ID del libro a prestar.
     * @param userId ID del usuario que lo solicita.
     * @return DTO actualizado del libro prestado.
     * @throws NoSuchElementException si el libro no existe.
     * @throws IllegalStateException si el libro ya está prestado.
     */
    public BookDTO borrowBook(Long bookId, Long userId) {
        BookDTO book = bookRepository.get(bookId);

        if (book == null) {
            throw new NoSuchElementException("Libro no encontrado con ID: " + bookId);
        }

        if (book.isBorrowed()) {
            throw new IllegalStateException("El libro ya está prestado.");
        }

        book.setBorrowed(true);
        book.setBorrowedByUserId(userId);
        return book;
    }
}
