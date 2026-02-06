package com.opticaApp.backend.api;

import com.opticaApp.backend.models.PhotoUploadDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/photo")
public interface PhotoApi {
    @PatchMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ResponseEntity<Map<String, String>> uploadPhoto(@ModelAttribute PhotoUploadDTO dto);

}
