package com.opticaApp.backend.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    //lo que se va quedar Tanstack
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String photoUrl;
    private String direccion;
}
