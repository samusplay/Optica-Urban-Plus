package com.opticaApp.backend.apicontroller;

import com.opticaApp.backend.api.PhotoApi;
import com.opticaApp.backend.models.PhotoUploadDTO;
import com.opticaApp.backend.security.UserDetailsImpl;
import com.opticaApp.backend.service.UploadPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PhotoApiController implements PhotoApi {
    private final UploadPhotoService uploadPhotoService;
    @Override
    public ResponseEntity<Map<String, String>> uploadPhoto(PhotoUploadDTO dto) {
        // Extraemos el ID del token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        //llamar al servicio
        String url = uploadPhotoService.uploadProfilePhoto(userDetails.getUser().getId(), dto);

        //retornamos respuesta
        return ResponseEntity.ok(Map.of("url", url));
    }
}
