package com.bibliosoft.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object que representa a un autor en el sistema de biblioteca.
 */
@Schema(description = "Representa un autor registrado en el sistema de biblioteca.")
public class AuthorDTO {

    @Schema(
        description = "Identificador único del autor.",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
        description = "Nombre completo del autor.",
        example = "Gabriel García Márquez",
        required = true
    )
    @NotNull(message = "El nombre del autor no puede ser nulo.")
    @NotBlank(message = "El nombre del autor no puede estar vacío.")
    @Size(min = 2, max = 100, message = "El nombre del autor debe tener entre 2 y 100 caracteres.")
    private String name;

    /**
     * Constructor por defecto requerido para frameworks de serialización/deserialización.
     */
    public AuthorDTO() {
    }

    /**
     * Constructor completo.
     *
     * @param id   ID del autor.
     * @param name Nombre completo del autor.
     */
    public AuthorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retorna el identificador único del autor.
     * @return ID del autor.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del autor.
     * @param id ID del autor.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna el nombre completo del autor.
     * @return Nombre del autor.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre completo del autor.
     * @param name Nombre del autor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Representación en texto del DTO para propósitos de depuración.
     * @return String representando el estado del objeto.
     */
    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}