package com.bibliosoft.library.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bibliosoft.library.dto.AuthorDTO;
import com.bibliosoft.library.service.AuthorService;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Endpoint to get all authors
    @GetMapping
    public List<AuthorDTO> getAll() {
        return authorService.getAll();
    }

    @PostMapping
    public AuthorDTO add(@RequestBody AuthorDTO author) {
        return authorService.add(author);
    }

    @GetMapping("/{id}")
    public AuthorDTO getById(@PathVariable Long id) {
        return authorService.getById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!authorService.delete(id)) {
            throw new RuntimeException("Failed to delete author");
        }
    }
}
