-- =====================================================
-- Script DDL - Control de Inventario TechStore
-- Base de datos: techstore (MySQL 8.0)
-- =====================================================

DROP DATABASE IF EXISTS techstore;
CREATE DATABASE techstore DEFAULT CHARACTER SET utf8mb4;
USE techstore;

-- Tabla de Usuarios (Login y roles)
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol ENUM('Gerente', 'Bodeguero', 'Vendedor') NOT NULL,
    activo TINYINT(1) DEFAULT 1,
    INDEX idx_usuario_username (username)
) ENGINE=InnoDB;

-- Tabla de Categorías
CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- Tabla de Proveedores
CREATE TABLE proveedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    INDEX idx_proveedor_nombre (nombre)
) ENGINE=InnoDB;

-- Tabla de Productos
CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    umbral_minimo INT NOT NULL DEFAULT 5,
    categoria_id INT NOT NULL,
    proveedor_id INT NOT NULL,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id)
        REFERENCES categoria(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_producto_proveedor FOREIGN KEY (proveedor_id)
        REFERENCES proveedor(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    INDEX idx_producto_nombre (nombre),
    INDEX idx_producto_categoria (categoria_id),
    INDEX idx_producto_proveedor (proveedor_id)
) ENGINE=InnoDB;

-- Tabla de Órdenes de Compra
CREATE TABLE orden_compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proveedor_id INT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('Pendiente', 'Aprobada', 'Recibida', 'Cancelada') DEFAULT 'Pendiente',
    total DECIMAL(12,2) DEFAULT 0.00,
    CONSTRAINT fk_orden_proveedor FOREIGN KEY (proveedor_id)
        REFERENCES proveedor(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    INDEX idx_orden_estado (estado)
) ENGINE=InnoDB;

-- Tabla de Detalle de Orden de Compra
CREATE TABLE detalle_orden (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orden_compra_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_detalle_orden FOREIGN KEY (orden_compra_id)
        REFERENCES orden_compra(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id)
        REFERENCES producto(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

-- Tabla de Kardex de Movimientos
CREATE TABLE kardex_movimiento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT NOT NULL,
    tipo ENUM('ENTRADA', 'SALIDA') NOT NULL,
    cantidad INT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observacion VARCHAR(255),
    CONSTRAINT fk_kardex_producto FOREIGN KEY (producto_id)
        REFERENCES producto(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    INDEX idx_kardex_producto (producto_id),
    INDEX idx_kardex_tipo (tipo)
) ENGINE=InnoDB;
