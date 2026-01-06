package com.opticaApp.backend.entity;

import com.opticaApp.backend.models.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String emaiL;

    @Column(nullable = false)
    private String password;

    @Column(name = "telefono", length = 20)
    private String telefono;

    //Rol ENUM
    @Enumerated(EnumType.STRING) // Importante para que guarde "CLIENTE" y no "0"
    @Column(nullable = false)
    private Role rol;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;


}
