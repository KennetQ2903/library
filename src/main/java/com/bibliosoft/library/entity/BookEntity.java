package com.bibliosoft.library.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
@Schema(description = "Entidad que representa un libro en el sistema de biblioteca.")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del libro.")
    private Long id;

    @Size(min = 3, max = 100)
    @NotBlank
    @Schema(description = "Título del libro.")
    private String title;

    @ManyToOne
    @Schema(description = "Autor del libro.")
    private AuthorEntity author;

    @ManyToOne
    @Schema(description = "Usuario que ha tomado prestado el libro.")
    private UserEntity borrowedBy;

    @Schema(description = "Indica si el libro está prestado.")
    private boolean borrowed;
}
