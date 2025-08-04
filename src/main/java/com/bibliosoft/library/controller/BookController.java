package com.bibliosoft.library.controller;

import com.bibliosoft.library.dto.BookDTO;
import com.bibliosoft.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controlador REST para gestionar operaciones sobre libros en el sistema.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    /**
     * Inyección de dependencias por constructor.
     *
     * @param bookService Servicio de lógica para libros.
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Obtiene todos los libros registrados en el sistema.
     *
     * @return Lista de BookDTO.
     */
    @Operation(summary = "Obtiene la lista completa de libros.")
    @ApiResponse(responseCode = "200", description = "Lista de libros obtenida exitosamente.")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    /**
     * Registra un nuevo libro en el sistema.
     *
     * @param book DTO con los datos del libro (validados).
     * @return BookDTO creado con ID asignado.
     */
    @Operation(summary = "Registra un nuevo libro.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Libro creado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.")
    })
    @PostMapping
    public ResponseEntity<BookDTO> add(@RequestBody @Valid BookDTO book) {
        BookDTO saved = bookService.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Obtiene un libro específico por su ID.
     *
     * @param id ID del libro a buscar (debe ser >= 1).
     * @return BookDTO correspondiente.
     */
    @Operation(summary = "Obtiene un libro por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Libro encontrado."),
        @ApiResponse(responseCode = "404", description = "Libro no encontrado.")
    })
    @GetMapping("/{id}")
    public BookDTO getById(@PathVariable @Min(1) Long id) {
        return bookService.getById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));
    }

    /**
     * Elimina un libro por su ID.
     *
     * @param id ID del libro a eliminar.
     */
    @Operation(summary = "Elimina un libro por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Libro eliminado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Libro no encontrado.")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) Long id) {
        boolean deleted = bookService.delete(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se pudo eliminar el libro");
        }
    }

    /**
     * Permite a un usuario tomar prestado un libro específico.
     *
     * @param bookId ID del libro a prestar.
     * @param userId ID del usuario que lo solicita.
     * @return BookDTO actualizado con estado de préstamo.
     */
    @Operation(summary = "Marca un libro como prestado por un usuario.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Libro prestado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Libro o usuario no encontrado."),
        @ApiResponse(responseCode = "409", description = "El libro ya está prestado.")
    })
    @PostMapping("/{bookId}/borrow/{userId}")
    public BookDTO borrowBook(
        @PathVariable @Min(1) Long bookId,
        @PathVariable @Min(1) Long userId
    ) {
        try {
            return bookService.borrowBook(bookId, userId);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
