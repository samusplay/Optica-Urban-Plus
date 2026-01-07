package com.opticaApp.backend.service;

import com.opticaApp.backend.entity.Prescription;
import com.opticaApp.backend.models.PrescriptionUploadDTO;

public interface PrescriptionService {
    //firma del servicio un compromiso
    //Recibimos el dto y devolvemos la entidad
    Prescription uploadPrescription(PrescriptionUploadDTO dto);
}
