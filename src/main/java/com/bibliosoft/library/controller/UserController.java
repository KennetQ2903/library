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

import com.bibliosoft.library.entity.UserEntity;
import com.bibliosoft.library.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Controlador REST que expone operaciones CRUD para usuarios.
 * Punto de entrada: /api/users
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Inyección de dependencias vía constructor.
     *
     * @param userService Servicio de lógica de negocio para usuarios.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return Lista de UserDTO.
     */
    @Operation(summary = "Obtiene la lista de todos los usuarios.")
    @ApiResponse(responseCode = "200", description = "Usuarios obtenidos exitosamente.")
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    /**
     * Crea un nuevo usuario a partir del DTO recibido.
     *
     * @param user DTO validado con los datos del nuevo usuario.
     * @return Usuario creado con ID asignado.
     */
    @Operation(summary = "Registra un nuevo usuario.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado correctamente."),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.")
    })
    @PostMapping
    public ResponseEntity<UserEntity> add(@RequestBody @Valid UserEntity user) {
        UserEntity saved = userService.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario (debe ser mayor o igual a 1).
     * @return DTO del usuario encontrado.
     */
    @Operation(summary = "Obtiene un usuario por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable @Min(1) Long id) {
        return userService.getById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario (mínimo 1).
     */
    @Operation(summary = "Elimina un usuario por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente."),
        @ApiResponse(responseCode = "404", description = "No se encontró el usuario a eliminar.")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        boolean deleted = userService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se pudo eliminar el usuario");
        }
    }
}
