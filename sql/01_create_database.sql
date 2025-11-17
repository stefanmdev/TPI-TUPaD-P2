-- Script preparado por Joaquin Villarruel
CREATE DATABASE IF NOT EXISTS tp_p2_pedido_envio
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE tp_p2_pedido_envio;

-- Tabla Pedido (A)
CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado TINYINT(1) NOT NULL DEFAULT 0,
    numero VARCHAR(20) NOT NULL UNIQUE,
    fecha DATE NOT NULL,
    cliente_nombre VARCHAR(120) NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    estado ENUM('NUEVO', 'FACTURADO', 'ENVIADO') NOT NULL
);

-- Tabla Envio (B)
CREATE TABLE envio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado TINYINT(1) NOT NULL DEFAULT 0,
    tracking VARCHAR(40) UNIQUE,
    empresa ENUM('ANDREANI', 'OCA', 'CORREO_ARG'),
    tipo ENUM('ESTANDAR', 'EXPRES'),
    costo DECIMAL(10,2),
    fecha_despacho DATE,
    fecha_estimada DATE,
    estado ENUM('EN_PREPARACION', 'EN_TRANSITO', 'ENTREGADO'),
    pedido_id BIGINT UNIQUE,
    CONSTRAINT fk_envio_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedido(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

