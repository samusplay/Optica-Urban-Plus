package com.opticaApp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

//Respuesta del backend del detalle del producto
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailResponseDTO {
    private Long productId;

    private String nombre;

    private BigDecimal precio;

    private String imagenKey;

    private String descripcion;

    private Integer stock;
}
