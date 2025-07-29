package com.bibliosoft.library.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.bibliosoft.library.dto.AuthorDTO;

@Service
public class AuthorService {
    private final Map<Long, AuthorDTO> authors = new HashMap<>();
    private Long idCounter = 1L;

    public List<AuthorDTO> getAll() {
        return new ArrayList<>(authors.values());
    }

    public AuthorDTO add(AuthorDTO author) {
        author.setId(idCounter++);
        authors.put(author.getId(), author);
        return author;
    }

    public Optional<AuthorDTO> getById(Long id) {
        return Optional.ofNullable(authors.get(id));
    }

    public boolean delete(Long id) {
        return authors.remove(id) != null;
    }
}
