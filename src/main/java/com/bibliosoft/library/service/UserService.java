package com.bibliosoft.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliosoft.library.entity.UserEntity;
import com.bibliosoft.library.repository.UserRepository;

/**
 * Servicio que encapsula la lógica de negocio relacionada con usuarios,
 * ahora usando JPA y H2 en lugar de almacenamiento en memoria.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Devuelve la lista completa de usuarios registrados.
     *
     * @return Lista de usuarios.
     */
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param user DTO del usuario a crear.
     * @return Usuario creado.
     */
    public UserEntity add(UserEntity user) {
        return userRepository.save(user);
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID único del usuario.
     * @return Usuario si existe; null si no.
     */
    public Optional<UserEntity> getById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return true si el usuario fue eliminado; false si no existía.
     */
    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
