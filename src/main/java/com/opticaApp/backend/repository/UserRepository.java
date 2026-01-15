package com.opticaApp.backend.repository;

import com.opticaApp.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    //Metodo para Buscar el usuario por email
    Optional<User> findByEmail(String email);

    //Metodo para registrar :Validar duplicados

    boolean existsByEmail(String email);

    //Metodo para Buscar Por telefono
    boolean existsByTelefono(String telefono);
}
