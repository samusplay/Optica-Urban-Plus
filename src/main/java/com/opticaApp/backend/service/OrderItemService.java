package com.opticaApp.backend.service;

import com.opticaApp.backend.entity.Order;
import com.opticaApp.backend.models.OrderItemRequestDTO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemService {

    //procesa los items del carrito
    BigDecimal processAnSaveItems(List<OrderItemRequestDTO> itemsDto, Order saveOrder);

    //metodo cuando se cancela una orden
    void restoreStockForCanceledOrder(Long orderId);
}
