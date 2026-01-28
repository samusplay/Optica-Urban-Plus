package com.opticaApp.backend.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
//dto para subir la foto de perfil
public class PhotoUploadDTO {
    private MultipartFile file;
}
