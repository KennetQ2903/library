package com.bibliosoft.library.service;

import com.bibliosoft.library.dto.AuthorDTO;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Servicio que gestiona operaciones de negocio relacionadas con autores.
 * Usa una estructura de almacenamiento en memoria basada en Map.
 */
@Service
public class AuthorService {

    /**
     * Almacenamiento en memoria de los autores (simulación de base de datos).
     * Clave: ID del autor, Valor: objeto AuthorDTO.
     */
    private final Map<Long, AuthorDTO> authorRepository = new HashMap<>();

    /**
     * Contador incremental para simular la asignación de IDs.
     */
    private Long idSequence = 1L;

    /**
     * Obtiene la lista completa de autores registrados.
     *
     * @return Lista de objetos AuthorDTO.
     */
    public List<AuthorDTO> getAll() {
        return new ArrayList<>(authorRepository.values());
    }

    /**
     * Registra un nuevo autor. El ID se genera automáticamente.
     *
     * @param author DTO del autor sin ID.
     * @return DTO del autor con ID asignado.
     */
    public AuthorDTO add(AuthorDTO author) {
        author.setId(idSequence++);
        authorRepository.put(author.getId(), author);
        return author;
    }

    /**
     * Busca un autor por su ID.
     *
     * @param id ID único del autor.
     * @return Optional con AuthorDTO si se encuentra; vacío en caso contrario.
     */
    public Optional<AuthorDTO> getById(Long id) {
        return Optional.ofNullable(authorRepository.get(id));
    }

    /**
     * Elimina un autor por su ID.
     *
     * @param id ID del autor a eliminar.
     * @return true si el autor fue eliminado; false si no existía.
     */
    public boolean delete(Long id) {
        return authorRepository.remove(id) != null;
    }
}
