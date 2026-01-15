package com.opticaApp.backend.service;

import com.opticaApp.backend.models.AuthResponseDTO;
import com.opticaApp.backend.models.LoginRequestDTO;
import com.opticaApp.backend.models.RegisterRequestDTO;

public interface AuthService {
    //firma de Crear nuevos Usuarios
    AuthResponseDTO register(RegisterRequestDTO request);

    //Validar Credeciales(Login)
    AuthResponseDTO login(LoginRequestDTO request);
}
