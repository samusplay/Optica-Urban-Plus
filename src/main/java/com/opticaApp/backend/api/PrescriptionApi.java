package com.opticaApp.backend.api;

import com.opticaApp.backend.entity.Prescription;
import com.opticaApp.backend.models.PrescriptionUploadDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/prescriptions")
public interface PrescriptionApi {

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE // <--- ESTO ES OBLIGATORIO
    )
    ResponseEntity<Prescription> uploadPrescription(
            // Usamos @ModelAttribute porque NO es un JSON, es un Formulario con Archivo
            @ModelAttribute PrescriptionUploadDTO dto
    );
}
