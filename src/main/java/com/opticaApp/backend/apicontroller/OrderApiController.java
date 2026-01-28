package com.opticaApp.backend.apicontroller;

import com.opticaApp.backend.api.OrderApi;
import com.opticaApp.backend.entity.User;
import com.opticaApp.backend.models.OrderResponse;
import com.opticaApp.backend.security.UserDetailsImpl;
import com.opticaApp.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

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
}
