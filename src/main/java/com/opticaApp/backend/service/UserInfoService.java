package com.opticaApp.backend.service;

import com.opticaApp.backend.models.UserResponseDTO;

public interface UserInfoService {
    //llamamos dto +funcionalidad+parametros
    UserResponseDTO getUserInfo(Long userId);
}
