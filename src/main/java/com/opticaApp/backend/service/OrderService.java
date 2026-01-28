package com.opticaApp.backend.service;

import com.opticaApp.backend.models.OrderResponse;

import java.util.List;

public interface OrderService {

    //LISTAR ORDENES DEL USARIO
    List<OrderResponse> getUserOrders(Long userId);
}
