package com.opticaApp.backend.service.impl;

import com.opticaApp.backend.entity.User;
import com.opticaApp.backend.exceptions.ResourceNotFoundException;
import com.opticaApp.backend.models.PhotoUploadDTO;
import com.opticaApp.backend.repository.UserRepository;
import com.opticaApp.backend.service.UploadPhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadPhotoServiceImpl implements UploadPhotoService {
    private final UserRepository userRepository;
    private final S3Client s3Client;

    @Value("${aws.s3.bucket.profiles}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;
    @Override
    @Transactional
    public String uploadProfilePhoto(Long userId, PhotoUploadDTO dto) {
        // 1. Validar si viene el archivo dentro del DTO
        if (dto.getFile() == null || dto.getFile().isEmpty()) {
            throw new ResourceNotFoundException("Error: No se ha seleccionado ninguna imagen.");
        }

        // 2. Validar formato
        String contentType = dto.getFile().getContentType();
        if (!isValidImageFormat(contentType)) {
            throw new ResourceNotFoundException("Error: Formato no válido. Solo se permiten imágenes JPG, PNG o WEBP.");
        }

        // 3. Buscar usuario (Usando el ID que viene del Token, no del DTO, por seguridad)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // 4. Preparar ruta S3: profiles/user-1/uuid-foto.jpg
        String fileName = UUID.randomUUID() + "-" + dto.getFile().getOriginalFilename();
        String s3Key = "profiles/user-" + user.getId() + "/" + fileName;

        // 5. Subir a S3
        try {
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putOb,
                    RequestBody.fromInputStream(dto.getFile().getInputStream(), dto.getFile().getSize()));

            log.info("Foto subida exitosamente: {}", s3Key);

        } catch (IOException e) {
            throw new RuntimeException("Error subiendo foto a S3", e);
        }

        // 6. Generar URL Pública y Guardar en BD
        String publicUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, s3Key);

        user.setPhoto_url(publicUrl);
        userRepository.save(user);

        return publicUrl;
    }

    //metodo privado para validar el tipo de formato de la photo
    private boolean isValidImageFormat(String contentType) {
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/webp")
        );
    }
}
