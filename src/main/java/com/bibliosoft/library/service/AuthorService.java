package com.bibliosoft.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliosoft.library.entity.AuthorEntity;
import com.bibliosoft.library.repository.AuthorRepository;

/**
 * Servicio que gestiona operaciones de negocio relacionadas con autores,
 * ahora utilizando JPA en lugar de almacenamiento en memoria.
 */
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Obtiene la lista completa de autores registrados.
     *
     * @return Lista de autors.
     */
    public List<AuthorEntity> getAll() {
        return authorRepository.findAll();
    }

    /**
     * Registra un nuevo autor. El ID se genera automáticamente.
     *
     * @param author DTO del autor a crear.
     * @return Autor creado.
     */
    public AuthorEntity  add(AuthorEntity author) {
        return authorRepository.save(author);
    }
    
    /**
     * Busca un autor por su ID.
     *
     * @param id ID único del autor.
     * @return Optional con AuthorDTO si se encuentra; vacío en caso contrario.
     */
    public Optional<AuthorEntity> getById(Long id) {
        return authorRepository.findById(id);
    }

    /**
     * Elimina un autor por su ID.
     *
     * @param id ID del autor a eliminar.
     * @return true si el autor fue eliminado; false si no existía.
     */
    public boolean delete(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
