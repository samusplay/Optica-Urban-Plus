package com.opticaApp.backend.service.impl;

import com.opticaApp.backend.entity.User;
import com.opticaApp.backend.exceptions.ConflictException;
import com.opticaApp.backend.models.AuthResponseDTO;
import com.opticaApp.backend.models.LoginRequestDTO;
import com.opticaApp.backend.models.RegisterRequestDTO;
import com.opticaApp.backend.models.Role;
import com.opticaApp.backend.security.JwtService;
import com.opticaApp.backend.security.UserDetailsImpl;
import com.opticaApp.backend.service.AuthService;
import com.opticaApp.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userservice;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    //Herramienta Srping Security
    private final AuthenticationManager autenticacionManager;
    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        //Validamos el Correo electronico
        if(userservice.existsByEmail(request.getEmail())){
            throw  new RuntimeException("El correo electronico ya está registrado");
        }
        //validamos el numero de telefono
        if (request.getTelefono() != null && !request.getTelefono().isBlank()
                && userservice.existsByTelefono(request.getTelefono())) {
            throw new ConflictException("El teléfono ya está registrado por otro usuario");
        }
        //Construimos la identidad apartir del dto
        User user=User.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .telefono(request.getTelefono())
                .rol(Role.CLIENTE)
                .build();
        //Guardamos en la base de datos se lo delegamos UserService
        User savedUser=userservice.save(user);
        //Generemos token de seguridad
        String token=jwtService.generateToken(new UserDetailsImpl(savedUser));

        //Retornamos una respuesta
        return AuthResponseDTO.builder()
                .token(token)
                .nombre(savedUser.getNombre())
                .role(savedUser.getRol().name())
                .build();
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        //Logica del Login
        //Metodo para verificar
        autenticacionManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //Buscamos en la base de datos el usuario para traer datos
        User user=userservice.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("Usuario no encontrado"));

        //Generamos el Token se refresca
        String token=jwtService.generateToken(new UserDetailsImpl(user));

        //Retornamos la respuesta
        return AuthResponseDTO.builder()
                .token(token)
                .id(user.getId())
                .nombre(user.getNombre())
                .role(user.getRol().name())
                .build();


    }
}
