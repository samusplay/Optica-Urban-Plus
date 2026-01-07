package com.opticaApp.backend.service.impl;

import com.opticaApp.backend.entity.Prescription;
import com.opticaApp.backend.entity.User;
import com.opticaApp.backend.models.PrescriptionUploadDTO;
import com.opticaApp.backend.repository.PrescriptionRepository;
import com.opticaApp.backend.repository.UserRepository;
import com.opticaApp.backend.service.PrescriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
//Logs
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    //Inyectar Repo de  user
    private final UserRepository userRepository;
    private final S3Client s3Client;

    //Inyencion del nombre del bucket de s3
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
    @Override
    @Transactional
    public Prescription uploadPrescription(PrescriptionUploadDTO dto) {
        //Validar si el usuario mando un archivo
        if(dto.getFile()==null||dto.getFile().isEmpty()){
            throw  new RuntimeException("Error:No se ha selecionado ningun archivo para subir ");
        }
        //Validar el formato pdf o word

        String contentType=dto.getFile().getContentType();
        if(!isValidPrescriptionFormat(contentType)){
            throw  new RuntimeException("Error:Formato no validado.Solo se permiten archivos PDF o Word");
        }

        //1validar  que exista un usuario y obtener Id
        User user=userRepository.findById(dto.getUserId())
                .orElseThrow(()->new RuntimeException("Usuario no encontrado"));

        //2.crear sub carpeta con el nombre del usuario
        //Limpiamos el nombre
        String cleanName = user.getNombre().trim().toLowerCase().replace(" ", "_");
        //Creamos estructura de carpetas
        String folderPath = "formulas/paciente-" + user.getId() + "-" + cleanName + "/";

        // Nombre final del archivo: uuid-nombreOriginal.pdf
        String fileName = UUID.randomUUID() + "-" + dto.getFile().getOriginalFilename();
        //llave de s3
        String s3Key=folderPath+fileName;

        //Subir a s3
        try {
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(dto.getFile().getContentType())
                    .build();

            s3Client.putObject(putOb,
                    RequestBody.fromInputStream(dto.getFile().getInputStream(), dto.getFile().getSize()));

            log.info("Archivo subido: {}", s3Key);

        } catch (IOException e) {
            throw new RuntimeException("Error subiendo archivo a S3", e);
        }

        //Persistencia y mapeo en la base de datos
        Prescription prescription=new Prescription();
        prescription.setUser(user);
        prescription.setFilekey(s3Key);
        prescription.setObservaciones(dto.getObservaciones());
        prescription.setFechaEmision(dto.getFechaEmision()!= null ? dto.getFechaEmision() : LocalDate.now());

        //Retornnamos y guardamos en la base de datos
        return prescriptionRepository.save(prescription);


    }
    //Metodo externo para validar si se sube el tipo de formato adecuado
    private boolean isValidPrescriptionFormat(String contentType) {
        return contentType != null && (
                contentType.equals("application/pdf") ||
                        contentType.equals("application/msword") || // .doc antiguo
                        contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") // .docx moderno
        );
    }
}
