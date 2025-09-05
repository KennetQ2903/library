package com.bibliosoft.library.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bibliosoft.library.entity.AuthorEntity;
import com.bibliosoft.library.repository.AccountRepository;
import com.bibliosoft.library.repository.TokenRepository;
import com.bibliosoft.library.service.AuthorService;
import com.bibliosoft.library.service.JwtService;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void shouldReturnListOfAuthors() throws Exception {
        List<AuthorEntity> authors = Arrays.asList(
                new AuthorEntity(1L, "Gabriel García Márquez", null),
                new AuthorEntity(2L, "Isabel Allende", null));

        Mockito.when(authorService.getAll()).thenReturn(authors);

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Gabriel García Márquez"));
    }

    @Test
    public void shouldAddNewAuthor() throws Exception {
        AuthorEntity newAuthor = new AuthorEntity(null, "Mario Vargas Llosa", null);
        AuthorEntity savedAuthor = new AuthorEntity(3L, "Mario Vargas Llosa", null);

        Mockito.when(authorService.add(Mockito.any(AuthorEntity.class)))
                .thenReturn(savedAuthor);

        mockMvc.perform(post("/api/authors")
                .contentType("application/json")
                .content("{\"name\":\"Mario Vargas Llosa\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Mario Vargas Llosa"));
    }

    @Test
    public void shouldGetAuthorById() throws Exception {
        AuthorEntity author = new AuthorEntity(1L, "Gabriel García Márquez", null);
        Mockito.when(authorService.getById(1L))
                .thenReturn(java.util.Optional.of(author));

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gabriel García Márquez"));
    }

    @Test
    public void shouldReturn404WhenAuthorNotFound() throws Exception {
        Mockito.when(authorService.getById(99L))
                .thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/authors/99"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Autor no encontrado"));
    }

    @Test
    public void shouldDeleteAuthor() throws Exception {
        Mockito.when(authorService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingAuthor() throws Exception {
        Mockito.when(authorService.delete(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/authors/99"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("No se pudo eliminar el autor"));
    }

}
