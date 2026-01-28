package com.opticaApp.backend.api;

import com.opticaApp.backend.models.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/order")
public interface OrderApi {

    //obtener las ordenes del usuario
    @GetMapping("/list")
    ResponseEntity<List<OrderResponse>>getQMyOrders();
}
