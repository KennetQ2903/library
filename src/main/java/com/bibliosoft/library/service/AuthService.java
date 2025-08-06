package com.bibliosoft.library.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bibliosoft.library.entity.AccountEntity;
import com.bibliosoft.library.entity.TokenEntity;
import com.bibliosoft.library.repository.AccountRepository;
import com.bibliosoft.library.repository.TokenRepository;
import com.bibliosoft.library.request.LoginRequest;
import com.bibliosoft.library.request.RegisterRequest;
import com.bibliosoft.library.response.TokenResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest request) {
        var account = AccountEntity.builder()
        .username(request.username())
        .password(passwordEncoder.encode(request.password()))
        .build();
        
        var savedAccount = accountRepository.save(account);
        var jwtToken = jwtService.generateToken(account);
        var refreshToken = jwtService.generateRefreshToken(account);
        saveUserToken(savedAccount, jwtToken);

        return new TokenResponse(jwtToken, refreshToken);
    }
    
    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
            )
        );
        var account = accountRepository.findByUsername(request.username()).orElseThrow();
        var jwtToken = jwtService.generateToken(account);
        var refreshToken = jwtService.generateRefreshToken(account);
        revokeAllUserTokens(account);
        saveUserToken(account, jwtToken);

        return new TokenResponse(jwtToken, refreshToken);
    }

    public void saveUserToken(AccountEntity account, String jwtToken) {
        var token = TokenEntity
        .builder()
        .account(account)
        .token(jwtToken)
        .tokenType(TokenEntity.TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final AccountEntity account) {
        final List<TokenEntity> validUserTokens = tokenRepository
                .findAllValidIsFalseOrRevokedIsFalseByAccountId(account.getId());
        if (!validUserTokens.isEmpty()) {
            for (final TokenEntity token : validUserTokens) {
                token.setRevoked(true);
                token.setExpired(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse refreshToken(final String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("El token no es un JWT válido");
        }
        final String refreshToken = authHeader.substring(7);
        final String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new IllegalArgumentException("El refresh token no es válido");
        }

        final AccountEntity account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        
        if (!jwtService.isTokenValid(refreshToken, account)) {
            throw new IllegalArgumentException("El refresh token no es válido");
        }

        final String accessToken = jwtService.generateToken(account);
        revokeAllUserTokens(account);
        saveUserToken(account, accessToken);

        return new TokenResponse(accessToken, refreshToken);
    }
}
