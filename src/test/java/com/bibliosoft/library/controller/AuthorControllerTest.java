package com.bibliosoft.library.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bibliosoft.library.entity.AuthorEntity;
import com.bibliosoft.library.service.AuthorService;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    public void shouldReturnListOfAuthors() throws Exception {
        List<AuthorEntity> authors = Arrays.asList(
            new AuthorEntity(1L, "Gabriel García Márquez", null),
            new AuthorEntity(2L, "Isabel Allende", null)
        );

        Mockito.when(authorService.getAll()).thenReturn(authors);

        mockMvc.perform(get("/api/authors"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].name").value("Gabriel García Márquez"));
    }
}
