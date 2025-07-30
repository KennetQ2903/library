package com.bibliosoft.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO que representa un libro en el sistema de biblioteca.
 */
@Schema(description = "Objeto de transferencia de datos que representa la información de un libro.")
public class BookDTO {
    @Schema(
        description = "Identificador único del libro.",
        example = "101",
        accessMode = Schema.AccessMode.READ_WRITE
    )
    @NotBlank(message = "El id es unico y no puede estar vacío.")
    private Long id;

    @Schema(
        description = "Título completo del libro.",
        example = "Cien Años de Soledad",
        required = true
    )
    @NotBlank(message = "El título no debe estar vacío.")
    private String title;

    @Schema(
        description = "Identificador único del autor del libro. Hace referencia a un AuthorDTO.",
        example = "5",
        required = true
    )
    @NotBlank(message = "El autor no debe estar vacío.")
    private Long authorId;

    @Schema(
        description = "Identificador del usuario que ha tomado prestado el libro, si aplica. Hace referencia a un UserDTO.",
        example = "20",
        nullable = true
    )
    private Long borrowedByUserId;

    @Schema(
        description = "Indica si el libro está actualmente prestado.",
        example = "true",
        defaultValue = "false"
    )
    private boolean borrowed;

    public BookDTO() {
    }
    
    public BookDTO(Long id, String title, Long authorId, Long borrowedByUserId, boolean borrowed) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.borrowedByUserId = borrowedByUserId;
        this.borrowed = borrowed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getBorrowedByUserId() {
        return borrowedByUserId;
    }

    public void setBorrowedByUserId(Long borrowedByUserId) {
        this.borrowedByUserId = borrowedByUserId;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", borrowedByUserId=" + borrowedByUserId +
                ", borrowed=" + borrowed +
                '}';
    }
}
