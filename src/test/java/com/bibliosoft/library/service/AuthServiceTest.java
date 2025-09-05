package com.bibliosoft.library.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bibliosoft.library.entity.AccountEntity;
import com.bibliosoft.library.entity.TokenEntity;
import com.bibliosoft.library.repository.AccountRepository;
import com.bibliosoft.library.repository.TokenRepository;
import com.bibliosoft.library.request.LoginRequest;
import com.bibliosoft.library.request.RegisterRequest;
import com.bibliosoft.library.response.TokenResponse;

public class AuthServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegister() {
        RegisterRequest request = new RegisterRequest("juan123", "password123");
        AccountEntity savedAccount = new AccountEntity(1L, "juan123", "encodedPass", null);
        String token = "jwtToken";
        String refreshToken = "refreshToken";

        when(passwordEncoder.encode(request.password())).thenReturn("encodedPass");
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(savedAccount);
        when(jwtService.generateToken(any(AccountEntity.class))).thenReturn(token);
        when(jwtService.generateRefreshToken(any(AccountEntity.class))).thenReturn(refreshToken);
        when(tokenRepository.save(any(TokenEntity.class))).thenReturn(null);

        TokenResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals(token, response.accessToken());
        assertEquals(refreshToken, response.refreshToken());
    }

    @Test
    public void testLogin() {
        LoginRequest request = new LoginRequest("juan123", "password123");
        AccountEntity account = new AccountEntity(1L, "juan123", "encodedPass", null);
        String token = "jwtToken";
        String refreshToken = "refreshToken";

        // MOCK: authenticate devuelve un objeto Authentication simulado
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // o un mock de Authentication si quieres

        when(accountRepository.findByUsername("juan123")).thenReturn(Optional.of(account));
        when(jwtService.generateToken(account)).thenReturn(token);
        when(jwtService.generateRefreshToken(account)).thenReturn(refreshToken);
        when(tokenRepository.findAllValidIsFalseOrRevokedIsFalseByAccountId(1L)).thenReturn(new ArrayList<>());
        when(tokenRepository.save(any(TokenEntity.class))).thenReturn(null);

        TokenResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals(token, response.accessToken());
        assertEquals(refreshToken, response.refreshToken());
    }

    @Test(expected = AuthenticationException.class)
    public void testLogin_InvalidCredentials() {
        LoginRequest request = new LoginRequest("juan123", "wrongpass");

        // Simula que al autenticar lanza una AuthenticationException
        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new org.springframework.security.core.AuthenticationException("Credenciales inválidas") {
                });

        // Llama al login, debería lanzar la excepción
        authService.login(request);
    }

    @Test
    public void testRefreshToken() {
        String refreshToken = "refreshToken";
        String authHeader = "Bearer " + refreshToken;
        AccountEntity account = new AccountEntity(1L, "juan123", "encodedPass", null);
        String newAccessToken = "newAccessToken";

        when(jwtService.extractUsername(refreshToken)).thenReturn("juan123");
        when(accountRepository.findByUsername("juan123")).thenReturn(Optional.of(account));
        when(jwtService.isTokenValid(refreshToken, account)).thenReturn(true);
        when(jwtService.generateToken(account)).thenReturn(newAccessToken);
        when(tokenRepository.findAllValidIsFalseOrRevokedIsFalseByAccountId(1L)).thenReturn(new ArrayList<>());
        when(tokenRepository.save(any(TokenEntity.class))).thenReturn(null);

        TokenResponse response = authService.refreshToken(authHeader);

        assertNotNull(response);
        assertEquals(newAccessToken, response.accessToken());
        assertEquals(refreshToken, response.refreshToken());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testRefreshToken_UserNotFound() {
        String refreshToken = "refreshToken";
        String authHeader = "Bearer " + refreshToken;

        when(jwtService.extractUsername(refreshToken)).thenReturn("unknownUser");
        when(accountRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        authService.refreshToken(authHeader);
    }
}
