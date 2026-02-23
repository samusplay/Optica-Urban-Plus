package com.opticaApp.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal total;
    private PaymentMethod metodoPago;
    private OrderStatus estado;
    private String shippingAddress;
    private LocalDateTime fecha;
    //detalles productos
    private List<OrderItemResponse> items;
}
