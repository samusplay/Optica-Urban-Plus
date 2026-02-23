package com.opticaApp.backend.apicontroller;

import com.opticaApp.backend.api.OrderApi;
import com.opticaApp.backend.entity.User;
import com.opticaApp.backend.models.OrderRequestDTO;
import com.opticaApp.backend.models.OrderResponse;
import com.opticaApp.backend.security.UserDetailsImpl;
import com.opticaApp.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController implements OrderApi {
    private final OrderService orderService;
    @Override
    public ResponseEntity<List<OrderResponse>> getQMyOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 1. CORRECCIÓN: Hacemos cast a la clase que REALMENTE está en el contexto
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        // 2. Extraemos el ID o el Usuario desde el envoltorio
        // (Asumo que tu UserDetailsImpl tiene un método getUser() o getId())
        Long userId = userDetails.getUser().getId();
        // O si tienes el ID directo: userDetails.getId();

        List<OrderResponse> orders = orderService.getUserOrders(userId);

        return ResponseEntity.ok(orders);
    }

    @Override
    public ResponseEntity<OrderResponse> createOrder(OrderRequestDTO orderRequest, Principal principal) {
        //obtenemos el usuario autenticado
        Long userId=getAuthenticatedUserId();

        //llamamos al servicio
        OrderResponse response=orderService.createOrder(orderRequest, userId);

        //Devolvemos un 201 CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<OrderResponse> getOrderDetails(Long orderId, Principal principal) {
        Long userId=getAuthenticatedUserId();
        //llamamos al servicio
        OrderResponse response=orderService.getOrderDetails(orderId,userId);

        //devolver respuesta
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> cancelOrder(Long orderId, Principal principal) {
        Long userId=getAuthenticatedUserId();

        orderService.cancelOrder(orderId, userId);

        return ResponseEntity.ok("Orden cancelada con exito");
    }

    //metodo privado para manejar autenticacion
    private Long getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getUser().getId();
    }
}
