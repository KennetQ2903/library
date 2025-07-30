package com.bibliosoft.library.service;

import com.bibliosoft.library.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Servicio que encapsula la lógica de negocio relacionada con usuarios.
 * Actualmente utiliza almacenamiento en memoria simulando una base de datos.
 */
@Service
public class UserService {

    /**
     * Simula una base de datos en memoria de usuarios.
     * Clave: ID del usuario, Valor: instancia de UserDTO.
     */
    private final Map<Long, UserDTO> userRepository = new HashMap<>();

    /**
     * Contador secuencial para la generación de IDs únicos.
     */
    private Long idSequence = 1L;

    /**
     * Devuelve la lista completa de usuarios registrados.
     *
     * @return Lista de objetos UserDTO.
     */
    public List<UserDTO> getAll() {
        return new ArrayList<>(userRepository.values());
    }

    /**
     * Registra un nuevo usuario. Se genera automáticamente un ID único.
     *
     * @param user Objeto UserDTO con la información del nuevo usuario (sin ID).
     * @return UserDTO con ID asignado.
     */
    public UserDTO add(UserDTO user) {
        user.setId(idSequence++);
        userRepository.put(user.getId(), user);
        return user;
    }

    /**
     * Busca un usuario por su identificador único.
     *
     * @param id ID del usuario a buscar.
     * @return Optional que contiene el UserDTO si existe; vacío si no existe.
     */
    public Optional<UserDTO> getById(Long id) {
        return Optional.ofNullable(userRepository.get(id));
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return true si el usuario existía y fue eliminado; false si no existía.
     */
    public boolean delete(Long id) {
        return userRepository.remove(id) != null;
    }
}
