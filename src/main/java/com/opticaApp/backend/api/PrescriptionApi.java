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

    //Formdata
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Prescription>uploadPrescription(@ModelAttribute PrescriptionUploadDTO uploadDTO);
}
