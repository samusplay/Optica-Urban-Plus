package com.opticaApp.backend.service;

import com.opticaApp.backend.entity.User;

import java.util.Optional;

//Le va servir a la base de datos
public interface UserService {
    //Metodo Crear Un usuario
    User save(User user);

    //Validar Usando el Email
    boolean existsByEmail(String email);

    //Buscar por correo Y Devolvemos la entidad
    Optional<User> findByEmail(String email);

    //Si existe el telefono
    boolean existsByTelefono(String telefono);
}
