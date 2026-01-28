-- 1. Agregar URL de foto a la tabla USERS
ALTER TABLE users
ADD COLUMN photo_url VARCHAR(255) NULL DEFAULT NULL;

-- 2. Agregar dirección de envío a la tabla ORDERS
ALTER TABLE orders
ADD COLUMN shipping_address VARCHAR(255) NULL DEFAULT NULL;