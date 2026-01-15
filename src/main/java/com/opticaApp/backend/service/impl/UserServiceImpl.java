package com.opticaApp.backend.service.impl;

import com.opticaApp.backend.entity.User;
import com.opticaApp.backend.repository.UserRepository;
import com.opticaApp.backend.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService {
    private final UserRepository userRepository;
    @Override
    @Transactional
    public User save(User user) {
        //Validacion Integral
       if(user==null){
           throw new IllegalArgumentException("El usuario no puede ser nulo");
       }
       //Segunda Validacion de email
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El usuario debe tener un email");
        }
        //Guardamos en la base de datos con try catch
        try{
            return userRepository.save(user);

        }catch (DataIntegrityViolationException e){
            throw new RuntimeException("Error de Integridad:faltan datos obligatorios");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        if (email == null || email.isBlank()) return Optional.empty();
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByTelefono(String telefono) {
        if(telefono==null||telefono.isBlank()){
            return false;
        }
        return userRepository.existsByTelefono(telefono);
    }
}
