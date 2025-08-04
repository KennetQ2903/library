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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bibliosoft.library.entity.AuthorEntity;
import com.bibliosoft.library.entity.BookEntity;
import com.bibliosoft.library.service.BookService;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@Import(BookService.class)  // En lugar de @MockBean
public class BookControllerTest {

   @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void shouldReturnListOfBooks() throws Exception {
        AuthorEntity author1 = new AuthorEntity(1L, "Author One", null);
        AuthorEntity author2 = new AuthorEntity(2L, "Author Two", null);

        List<BookEntity> books = Arrays.asList(
            new BookEntity(1L, "Book One", author1, null, false),
            new BookEntity(2L, "Book Two", author2, null, false)
        );

        Mockito.when(bookService.getAll()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].title").value("Book One"))
               .andExpect(jsonPath("$[1].title").value("Book Two"));
    }
}