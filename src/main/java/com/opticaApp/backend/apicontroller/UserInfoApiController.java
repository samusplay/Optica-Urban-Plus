package com.opticaApp.backend.apicontroller;

import com.opticaApp.backend.api.UserInfoApi;
import com.opticaApp.backend.models.UserResponseDTO;
import com.opticaApp.backend.security.UserDetailsImpl;
import com.opticaApp.backend.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserInfoApiController implements UserInfoApi {
    private final UserInfoService userInfoService;
    @Override
    public ResponseEntity<UserResponseDTO> getUserInfo() {
       //obtener autenticacion
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //extraemos datos Sring
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        //obtenemos el id
        Long userId=userDetails.getUser().getId();

        //llamamos al servicio
        UserResponseDTO response=userInfoService.getUserInfo(userId);
        //devolvemos respuesta
        return ResponseEntity.ok(response);
    }
}
