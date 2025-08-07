package com.bibliosoft.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bibliosoft.library.entity.AuthorEntity;
import com.bibliosoft.library.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST que gestiona las operaciones CRUD relacionadas con autores.
 * Expone endpoints accesibles en /api/authors.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    /**
     * Obtiene la lista completa de autores.
     *
     * @return Lista de objetos AuthorDTO.
     */
    @Operation(summary = "Obtiene todos los autores registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente.")
    @GetMapping
    public ResponseEntity<List<AuthorEntity>> getAll() {
        return ResponseEntity.ok(authorService.getAll());
    }

    /**
     * Registra un nuevo autor.
     *
     * @param author DTO con datos validados del nuevo autor.
     * @return AuthorDTO creado con su ID asignado.
     */
    @Operation(summary = "Registra un nuevo autor.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Autor creado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.")
    })
    @PostMapping
    public ResponseEntity<AuthorEntity> add(@RequestBody @Valid AuthorEntity author) {
        AuthorEntity saved = authorService.add(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Obtiene los datos de un autor específico por su ID.
     *
     * @param id Identificador del autor (debe ser mayor o igual a 1).
     * @return AuthorDTO correspondiente al ID.
     */
    @Operation(summary = "Obtiene un autor por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autor encontrado."),
        @ApiResponse(responseCode = "404", description = "Autor no encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuthorEntity> getById(@PathVariable @Min(1) Long id) {
        return authorService.getById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado")
        );
    }

    /**
     * Elimina un autor por su ID.
     *
     * @param id Identificador del autor a eliminar.
     */
    @Operation(summary = "Elimina un autor por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Autor eliminado exitosamente."),
        @ApiResponse(responseCode = "404", description = "No se encontró el autor a eliminar.")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        boolean deleted = authorService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se pudo eliminar el autor");
        }
    }
}
