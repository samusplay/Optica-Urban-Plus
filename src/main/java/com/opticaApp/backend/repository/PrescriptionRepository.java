package com.opticaApp.backend.repository;

import com.opticaApp.backend.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {

    //Metodo para buscar las formulas por usuario por su id
    List<Prescription> findByUserId(Long userId);

    //Metodo para buscar por fecha de emision

    List<Prescription>findByFechaEmision(LocalDate fecha);
}
