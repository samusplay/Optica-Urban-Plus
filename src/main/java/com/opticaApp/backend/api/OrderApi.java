package com.opticaApp.backend.api;

import com.opticaApp.backend.models.OrderRequestDTO;
import com.opticaApp.backend.models.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/order")
public interface OrderApi {

    //obtener las ordenes del usuario
    @GetMapping("/list")
    ResponseEntity<List<OrderResponse>>getQMyOrders();

    //crear pedido
    @PostMapping("/create")
    ResponseEntity<OrderResponse>createOrder(@Valid @RequestBody OrderRequestDTO orderRequest, Principal principal);

    //ver detalle del pedido
    @GetMapping("/{orderId}")
    ResponseEntity<OrderResponse>getOrderDetails(@PathVariable("orderId")Long orderId, Principal principal);

    @PatchMapping("/cancel/{orderId}")
    ResponseEntity<String>cancelOrder(@PathVariable("orderId")Long orderId,Principal principal);
}
