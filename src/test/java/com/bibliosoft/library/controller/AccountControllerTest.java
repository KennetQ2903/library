package com.bibliosoft.library.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bibliosoft.library.entity.AccountEntity;
import com.bibliosoft.library.repository.AccountRepository;
import com.bibliosoft.library.repository.TokenRepository;
import com.bibliosoft.library.service.AccountService;
import com.bibliosoft.library.service.JwtService;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void shouldReturnAccountById() throws Exception {
        AccountEntity account = new AccountEntity(1L, "Cuenta Principal", "Test123!", new ArrayList<>());

        Mockito.when(accountService.getById(1L)).thenReturn(Optional.of(account));

        mockMvc.perform(get("/api/account/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("Cuenta Principal"))
                .andExpect(jsonPath("$.password").value("Test123!"));
    }

    @Test
    public void shouldReturn404WhenAccountNotFound() throws Exception {
        Mockito.when(accountService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/account/99"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Cuenta no encontrada"));
    }
}
