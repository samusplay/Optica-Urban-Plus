package com.opticaApp.backend.service;

import com.opticaApp.backend.models.PhotoUploadDTO;

public interface UploadPhotoService {
    //metodo para subir la photo de perfil con id
    String uploadProfilePhoto(Long userId, PhotoUploadDTO dto);
}
