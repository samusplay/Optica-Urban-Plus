package com.opticaApp.backend.apicontroller;

import com.opticaApp.backend.api.ProductApi;
import com.opticaApp.backend.models.ProductCatalogResponseDTO;
import com.opticaApp.backend.models.ProductDetailResponseDTO;
import com.opticaApp.backend.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class ProductApiController implements ProductApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<List<ProductCatalogResponseDTO>> getCatalog() {
        //Llamar al service
        List<ProductCatalogResponseDTO> catlog=productService.getCatalog();
        return ResponseEntity.ok(catlog);
    }

    @Override
    public ResponseEntity<ProductDetailResponseDTO> getProductDetail(Long id) {
        ProductDetailResponseDTO detail=productService.getProductDetail(id);
        return ResponseEntity.ok(detail);
    }
}
