-- V1__initial_schema.sql
-- NOTA: Se eliminó CREATE DATABASE y USE para que Flyway use la conexión configurada.

-- =======================
-- USERS
-- =======================
CREATE TABLE users (
  user_id     BIGINT NOT NULL AUTO_INCREMENT,
  nombre      VARCHAR(100)    NOT NULL,
  email       VARCHAR(150)    NOT NULL,
  password    VARCHAR(255)    NOT NULL,
  telefono    VARCHAR(20)     NULL,
  rol         ENUM('CLIENTE','ADMIN') NOT NULL,
  created_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (user_id),
  UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB;

-- =======================
-- PRODUCTS
-- =======================
CREATE TABLE products (
  product_id  BIGINT NOT NULL AUTO_INCREMENT,
  nombre      VARCHAR(150)    NOT NULL,
  descripcion TEXT           NULL,
  precio      DECIMAL(10,2)  NOT NULL,
  stock       INT            NOT NULL,
  activo      BOOLEAN        NOT NULL DEFAULT TRUE,
  imagen_key  VARCHAR(255)   NULL,
  created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (product_id),
  KEY idx_products_activo_stock (activo, stock)
) ENGINE=InnoDB;

-- =======================
-- PRESCRIPTIONS
-- =======================
CREATE TABLE prescriptions (
  prescription_id BIGINT NOT NULL AUTO_INCREMENT,
  user_id         BIGINT NOT NULL,
  file_key        VARCHAR(255)    NOT NULL,
  fecha_emision   DATE            NOT NULL,
  observaciones   TEXT            NULL,
  created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (prescription_id),
  CONSTRAINT fk_prescriptions_user
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =======================
-- ORDERS
-- =======================
CREATE TABLE orders (
  order_id         BIGINT NOT NULL AUTO_INCREMENT,
  user_id          BIGINT NOT NULL,
  prescription_id  BIGINT NULL,
  total            DECIMAL(10,2)   NOT NULL,
  estado           ENUM('PENDIENTE','PAGADO','ENVIADO','CANCELADO') NOT NULL,
  payment_method   ENUM('MERCADO_PAGO','PAYU') NOT NULL,
  created_at       TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at       TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (order_id),
  CONSTRAINT fk_orders_user
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_orders_prescription
    FOREIGN KEY (prescription_id) REFERENCES prescriptions(prescription_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =======================
-- ORDER_ITEMS
-- =======================
CREATE TABLE order_items (
  order_item_id   BIGINT NOT NULL AUTO_INCREMENT,
  order_id        BIGINT NOT NULL,
  product_id      BIGINT NOT NULL,
  cantidad        INT             NOT NULL,
  precio_unitario DECIMAL(10,2)   NOT NULL,
  sub_total       DECIMAL(10,2)   AS (cantidad * precio_unitario) STORED,

  PRIMARY KEY (order_item_id),
  UNIQUE KEY uk_order_items_order_product (order_id, product_id),
  CONSTRAINT fk_order_items_order
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_order_items_product
    FOREIGN KEY (product_id) REFERENCES products(product_id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB;