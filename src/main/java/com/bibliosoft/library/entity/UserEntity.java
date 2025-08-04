package com.bibliosoft.library.entity;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") // porque "user" es palabra reservada en SQL
@Schema(description = "Entidad que representa un usuario en el sistema de biblioteca.")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del usuario.")
    private Long id;

    @Size(min = 3, max = 20)
    @NotBlank
    @Schema(description = "Nombre del usuario.")
    private String name;

    @OneToMany(mappedBy = "borrowedBy")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Lista de libros prestados por el usuario.")
    private List<BookEntity> borrowedBooks;
}
