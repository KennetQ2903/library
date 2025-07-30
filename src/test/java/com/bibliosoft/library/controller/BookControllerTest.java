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

import com.bibliosoft.library.dto.BookDTO;
import com.bibliosoft.library.service.BookService;

// https://www.baeldung.com/spring-test-mockmvc
@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@Import(BookService.class)  // En lugar de @MockBean
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private BookService bookService;

    @Test
    public void shouldReturnListOfBooks() throws Exception {
        List<BookDTO> books = Arrays.asList(
            new BookDTO(1L, "Book One", 1L, null, false),
            new BookDTO(2L, "Book Two", 2L, null, false)
        );

        Mockito.when(bookService.getAll()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2));
    }
}