package com.opticaApp.backend.api;

import com.opticaApp.backend.models.UserResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/user",
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserInfoApi {

    @GetMapping("/info")
    ResponseEntity<UserResponseDTO>getUserInfo();
}
