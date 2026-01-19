package com.opticaApp.backend.apicontroller;

import com.opticaApp.backend.api.AuthApi;
import com.opticaApp.backend.models.AuthResponseDTO;
import com.opticaApp.backend.models.LoginRequestDTO;
import com.opticaApp.backend.models.RegisterRequestDTO;
import com.opticaApp.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthApiController implements AuthApi {
    private final AuthService authService;
    @Override
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Override
    public ResponseEntity<AuthResponseDTO> login(LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
