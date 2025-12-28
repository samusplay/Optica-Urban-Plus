package com.opticaApp.backend.service;

import com.opticaApp.backend.models.ProductCatalogResponseDTO;
import com.opticaApp.backend.models.ProductDetailResponseDTO;

import java.util.List;

public interface ProductService {
    //Firma de funcionalidades

    //Obtener TODOS
    List<ProductCatalogResponseDTO>getCatalog();

    //OBTENER POR ID
    ProductDetailResponseDTO getProductDetail(Long id);
}
