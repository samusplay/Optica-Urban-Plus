package com.opticaApp.backend.service;

import com.opticaApp.backend.models.OrderRequestDTO;
import com.opticaApp.backend.models.OrderResponse;

import java.util.List;

public interface OrderService {

    //LISTAR ORDENES DEL USARIO
    List<OrderResponse> getUserOrders(Long userId);

    //Crear Orden
    OrderResponse createOrder(OrderRequestDTO orderRequest,Long userId);

    //ver Detalle de la orden con formulas+ precios
    OrderResponse getOrderDetails(Long orderId,Long userId);

    //Cancelar orden
    void cancelOrder(Long orderId,Long userId);
}
