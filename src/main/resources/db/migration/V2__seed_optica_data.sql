-- V2__seed_optica_data.sql

-- 1. Usuarios (Admin y Clientes)
INSERT INTO users (nombre, email, password, telefono, rol) VALUES
('Angela De La Pava', 'angela@optica.com', 'admin123', '3001234567', 'ADMIN'),
('Samuel Play', 'samuel@cliente.com', 'pass123', '3159876543', 'CLIENTE');

-- 2. Productos: Lentes de Contacto
INSERT INTO products (nombre, descripcion, precio, stock, activo, imagen_key) VALUES
('Acuvue Oasys with HydraLuxe', 'Lentes diarios desechables - Caja x30. Ideal para ojos secos.', 215000.00, 40, true, 'lentes/acuvue-oasys-daily.jpg'),
('Biofinity Toric', 'Lentes mensuales para Astigmatismo - Caja x6. Alta permeabilidad al oxígeno.', 185000.00, 25, true, 'lentes/biofinity-toric.jpg'),
('Air Optix Colors', 'Lentes de contacto cosméticos con color (sin fórmula) - Caja x2.', 95000.00, 60, true, 'lentes/air-optix-colors.jpg'),
('Ultra Bausch + Lomb', 'Lentes mensuales para miopía/hipermetropía - Caja x6.', 160000.00, 15, true, 'lentes/ultra-bausch.jpg');

-- 3. Registro de una venta real
-- Samuel compra una caja de Acuvue Oasys y una de Biofinity Toric

-- CAMBIO: Quitamos prescription_id de aquí
INSERT INTO orders (user_id, total, estado, payment_method) VALUES
(2, 400000.00, 'PAGADO', 'MERCADO_PAGO');

-- CAMBIO: Agregamos prescription_id aquí (NULL por ahora)
INSERT INTO order_items (order_id, product_id, cantidad, precio_unitario, prescription_id) VALUES
(1, 1, 1, 215000.00, NULL),
(1, 2, 1, 185000.00, NULL);