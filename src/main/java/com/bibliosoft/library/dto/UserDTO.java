package com.bibliosoft.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO que representa a un usuario dentro del sistema de biblioteca.
 * Utilizado para operaciones de lectura, creación o actualización de usuarios.
 */
@Schema(description = "Objeto de transferencia de datos que representa la información de un usuario.")
public class UserDTO {

    @Schema(
        description = "Identificador único del usuario.",
        example = "100",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
        description = "Nombre completo del usuario.",
        example = "Juan Pérez",
        required = true
    )
    @NotNull(message = "El nombre no puede ser nulo.")
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String name;

    /**
     * Constructor por defecto. Necesario para frameworks de serialización.
     */
    public UserDTO() {
    }

    /**
     * Constructor completo.
     *
     * @param id   ID del usuario.
     * @param name Nombre completo del usuario.
     */
    public UserDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retorna el identificador del usuario.
     *
     * @return ID único del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param id Identificador único.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna el nombre completo del usuario.
     *
     * @return Nombre del usuario.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param name Nombre completo.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Representación en texto del objeto.
     *
     * @return Estado actual del DTO como String.
     */
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
