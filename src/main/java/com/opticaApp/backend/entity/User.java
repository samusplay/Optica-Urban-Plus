package com.opticaApp.backend.entity;

import com.opticaApp.backend.models.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.List;

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
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "telefono", length = 20)
    private String telefono;

    //Rol ENUM
    @Enumerated(EnumType.STRING) // Importante para que guarde "CLIENTE" y no "0"
    @Column(nullable = false)
    private Role rol;

    //agregar Photo_url

    @Column(name = "photo_url")
    private String photo_url;

    //relacion biderecional traemos el objeto
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;

    @Column(name = "created_at", updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime createdAt;



}
