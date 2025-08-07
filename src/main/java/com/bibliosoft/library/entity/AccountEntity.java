package com.bibliosoft.library.entity;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Schema(description = "Entidad que representa una cuenta en el sistema de biblioteca.")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la cuenta.")
    private Long id;

    @Size(min = 3, max = 20)
    @NotBlank
    @Column(unique = true)
    @Schema(description = "Username de la cuenta.")
    private String username;

    @Size(min = 8, max = 20)
    @NotBlank
    @Schema(description = "Contraseña de la cuenta.")
    private String password;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @Schema(description = "Lista de tokens de la cuenta.")
    private List<TokenEntity> tokens;
}
