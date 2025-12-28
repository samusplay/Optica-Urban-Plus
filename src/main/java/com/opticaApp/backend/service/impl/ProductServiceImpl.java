package com.opticaApp.backend.service.impl;

import com.opticaApp.backend.entity.Product;
import com.opticaApp.backend.models.ProductCatalogResponseDTO;
import com.opticaApp.backend.models.ProductDetailResponseDTO;
import com.opticaApp.backend.repository.ProductRepository;
import com.opticaApp.backend.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ClientInfoStatus;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    //inyectamos repositorio
    private final ProductRepository productRepository;

    @Override
    public List<ProductCatalogResponseDTO> getCatalog() {
        //Traemos de la base de datos los activos
        List<Product>products=productRepository.findByActivoTrue();

        //Usamos map para convertir la entidad al Dto
        return products.stream().
                map(p->ProductCatalogResponseDTO.builder()
                        .productId(p.getProductId())
                        .nombre(p.getNombre())
                        .precio(p.getPrecio())
                        .imagenKey(p.getImagenKey())
                        .build())
                .toList();
    }

    @Override
    public ProductDetailResponseDTO getProductDetail(Long id) {
        //creamos un objeto
        Product product = productRepository.findByProductIdAndActivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        //Retornamos con builder cada campo
        //Ventaja de usar el builder  evitamos errores de orden

        return ProductDetailResponseDTO.builder()
                .productId(product.getProductId())
                .nombre(product.getNombre())
                .precio(product.getPrecio())
                .imagenKey(product.getImagenKey())
                .descripcion(product.getDescripcion())
                .stock(product.getStock())
                .build();
    }
}
