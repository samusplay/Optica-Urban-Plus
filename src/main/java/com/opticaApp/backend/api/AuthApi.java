package com.opticaApp.backend.api;

import com.opticaApp.backend.models.AuthResponseDTO;
import com.opticaApp.backend.models.LoginRequestDTO;
import com.opticaApp.backend.models.RegisterRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/auth")
public interface AuthApi {

    //Endpoint firma de Registro
    @PostMapping("/register")
    ResponseEntity<AuthResponseDTO>register(@RequestBody RegisterRequestDTO request);

    @PostMapping("/login")
    ResponseEntity<AuthResponseDTO>login(@RequestBody LoginRequestDTO request);

}
