package com.opticaApp.backend.api;

import com.opticaApp.backend.models.ProductCatalogResponseDTO;
import com.opticaApp.backend.models.ProductDetailResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/products",
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface ProductApi {

    //Listar Catalogo
    @GetMapping
    ResponseEntity<List<ProductCatalogResponseDTO>>getCatalog();

    //Listar Por id

    @GetMapping(path = "/{id}")
    ResponseEntity<ProductDetailResponseDTO>getProductDetail(@PathVariable Long id);

}
