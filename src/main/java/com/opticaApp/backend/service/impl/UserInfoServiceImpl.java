package com.opticaApp.backend.service.impl;

import com.opticaApp.backend.entity.User;
import com.opticaApp.backend.exceptions.NotFoundException;
import com.opticaApp.backend.models.UserResponseDTO;
import com.opticaApp.backend.repository.UserRepository;
import com.opticaApp.backend.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserRepository userRepository;
    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserInfo(Long userId) {

        //buscar en la base de datos
        User user=userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("Usuario no encontrado con ID"+userId));

        //mapeamos de dto a entidad
        return UserResponseDTO.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .photoUrl(user.getPhoto_url())
                .direccion(user.getDireccion())
                .build();
    }
}
