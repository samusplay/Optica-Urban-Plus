package com.opticaApp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCatalogResponseDTO {
    private Long productId;
    private String nombre;
    private BigDecimal precio;
    private String imagenKey;
}
