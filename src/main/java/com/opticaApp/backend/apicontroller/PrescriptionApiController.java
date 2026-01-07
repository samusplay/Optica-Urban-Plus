package com.opticaApp.backend.apicontroller;

import com.opticaApp.backend.api.PrescriptionApi;
import com.opticaApp.backend.entity.Prescription;
import com.opticaApp.backend.models.PrescriptionUploadDTO;
import com.opticaApp.backend.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PrescriptionApiController implements PrescriptionApi {
    //inyectamos el servicio
    private final PrescriptionService prescriptionService;
    @Override
    public ResponseEntity<Prescription> uploadPrescription(PrescriptionUploadDTO uploadDTO) {
        log.info("Controlador:Recibiendo solicitud de subida para User ID:{}",uploadDTO.getUserId());

        //llamamos al servicio
        Prescription newPrescription=prescriptionService.uploadPrescription(uploadDTO);

        //Devolvemos una respuesta 201
        return ResponseEntity.status(HttpStatus.CREATED).body(newPrescription);
    }
}
