
package com.bibliosoft.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bibliosoft.library.entity.AccountEntity;
import com.bibliosoft.library.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Obtiene una cuenta por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cuenta encontrada."),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AccountEntity> getAccount(@PathVariable @Min(1) Long id) {
        return accountService.getById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada")
        );
    }
}
