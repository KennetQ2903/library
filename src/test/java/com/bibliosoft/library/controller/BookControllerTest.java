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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bibliosoft.library.entity.AuthorEntity;
import com.bibliosoft.library.entity.BookEntity;
import com.bibliosoft.library.repository.AccountRepository;
import com.bibliosoft.library.repository.TokenRepository;
import com.bibliosoft.library.service.BookService;
import com.bibliosoft.library.service.JwtService;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@Import(BookService.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void shouldReturnListOfBooks() throws Exception {
        AuthorEntity author1 = new AuthorEntity(1L, "Author One", null);
        AuthorEntity author2 = new AuthorEntity(2L, "Author Two", null);

        List<BookEntity> books = Arrays.asList(
                new BookEntity(1L, "Book One", author1, null, false),
                new BookEntity(2L, "Book Two", author2, null, false));

        Mockito.when(bookService.getAll()).thenReturn(books);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Book One"))
                .andExpect(jsonPath("$[1].title").value("Book Two"));
    }

    @Test
    public void shouldAddNewBook() throws Exception {
        AuthorEntity author = new AuthorEntity(1L, "Author One", null);
        BookEntity newBook = new BookEntity(null, "New Book", author, null, false);
        BookEntity savedBook = new BookEntity(3L, "New Book", author, null, false);

        Mockito.when(bookService.add(Mockito.any(BookEntity.class))).thenReturn(savedBook);

        mockMvc.perform(post("/api/books")
                .contentType("application/json")
                .content("{\"title\":\"New Book\",\"author\":{\"id\":1,\"name\":\"Author One\"}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    public void shouldGetBookById() throws Exception {
        AuthorEntity author = new AuthorEntity(1L, "Author One", null);
        BookEntity book = new BookEntity(1L, "Book One", author, null, false);

        Mockito.when(bookService.getById(1L)).thenReturn(java.util.Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Book One"));
    }

    @Test
    public void shouldReturn404WhenBookNotFound() throws Exception {
        Mockito.when(bookService.getById(99L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Libro no encontrado"));
    }

    @Test
    public void shouldDeleteBook() throws Exception {
        Mockito.when(bookService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingBook() throws Exception {
        Mockito.when(bookService.delete(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("No se pudo eliminar el libro"));
    }

    @Test
    public void shouldBorrowBookSuccessfully() throws Exception {
        AuthorEntity author = new AuthorEntity(1L, "Author One", null);
        BookEntity borrowedBook = new BookEntity(1L, "Book One", author, null, true);

        Mockito.when(bookService.borrowBook(1L, 10L)).thenReturn(borrowedBook);

        mockMvc.perform(post("/api/books/1/borrow/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.borrowed").value(true));
    }

    @Test
    public void shouldReturn409WhenBookAlreadyBorrowed() throws Exception {
        Mockito.when(bookService.borrowBook(1L, 10L))
                .thenThrow(new IllegalStateException("El libro ya está prestado"));

        mockMvc.perform(post("/api/books/1/borrow/10"))
                .andExpect(status().isConflict())
                .andExpect(status().reason("El libro ya está prestado"));
    }

    @Test
    public void shouldReturn404WhenBookOrUserNotFound() throws Exception {
        Mockito.when(bookService.borrowBook(1L, 10L))
                .thenThrow(new java.util.NoSuchElementException("Libro o usuario no encontrado"));

        mockMvc.perform(post("/api/books/1/borrow/10"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Libro o usuario no encontrado"));
    }

}