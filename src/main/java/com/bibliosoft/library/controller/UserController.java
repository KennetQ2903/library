package com.bibliosoft.library.controller;

import com.bibliosoft.library.dto.UserDTO;
import com.bibliosoft.library.service.UserService;
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
    public ResponseEntity<List<UserDTO>> getAll() {
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
    public ResponseEntity<UserDTO> add(@RequestBody @Valid UserDTO user) {
        UserDTO saved = userService.add(user);
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
    public UserDTO getById(@PathVariable @Min(1) Long id) {
        return userService.getById(id)
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
    public void delete(@PathVariable @Min(1) Long id) {
        boolean deleted = userService.delete(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se pudo eliminar el usuario");
        }
    }
}
