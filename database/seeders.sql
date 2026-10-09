-- =====================================================
-- Seeders - Datos de Prueba TechStore (MySQL 8.0)
-- Al menos 5 registros por tabla
-- =====================================================
USE techstore;

-- Usuarios (3 roles)
INSERT INTO usuario (username, password, rol) VALUES
('gerente1', 'admin123', 'Gerente'),
('gerente2', 'admin456', 'Gerente'),
('bodeguero1', 'bode123', 'Bodeguero'),
('bodeguero2', 'bode456', 'Bodeguero'),
('vendedor1', 'vend123', 'Vendedor'),
('vendedor2', 'vend456', 'Vendedor'),
('vendedor3', 'vend789', 'Vendedor');

-- Categorías
INSERT INTO categoria (nombre) VALUES
('Laptops'),
('Smartphones'),
('Tablets'),
('Accesorios'),
('Componentes'),
('Periféricos'),
('Redes');

-- Proveedores
INSERT INTO proveedor (nombre, telefono, email) VALUES
('TechDistribuidora GT', '2234-5678', 'ventas@techdist.gt'),
('ElectroImport S.A.', '2345-6789', 'info@electroimport.com'),
('CompuMayor', '2456-7890', 'pedidos@compumayor.com'),
('Digital Supply', '2567-8901', 'orders@digitalsupply.com'),
('MegaTech Wholesale', '2678-9012', 'wholesale@megatech.com');

-- Productos
INSERT INTO producto (nombre, precio, stock, umbral_minimo, categoria_id, proveedor_id) VALUES
('Laptop HP Pavilion 15', 5499.99, 25, 5, 1, 1),
('Laptop Lenovo IdeaPad 3', 4299.00, 18, 5, 1, 2),
('Samsung Galaxy S24', 6999.99, 30, 8, 2, 3),
('iPhone 15 Pro', 9499.00, 12, 4, 2, 4),
('iPad Air M2', 5299.00, 15, 5, 3, 4),
('Mouse Logitech MX Master', 699.99, 50, 10, 4, 1),
('Teclado Mecánico HyperX', 549.00, 40, 10, 4, 2),
('SSD Samsung 1TB', 899.00, 35, 8, 5, 3),
('RAM Kingston 16GB DDR5', 459.00, 60, 15, 5, 5),
('Monitor Dell 27" 4K', 3299.00, 10, 3, 6, 1),
('Router TP-Link AX6000', 1299.00, 20, 5, 7, 5);

-- Órdenes de Compra
INSERT INTO orden_compra (proveedor_id, fecha, estado, total) VALUES
(1, '2026-09-01 10:00:00', 'Recibida', 27499.95),
(2, '2026-09-05 14:30:00', 'Recibida', 21495.00),
(3, '2026-09-10 09:00:00', 'Aprobada', 34999.95),
(4, '2026-09-15 11:00:00', 'Pendiente', 47495.00),
(5, '2026-09-20 16:00:00', 'Pendiente', 6885.00);

-- Detalles de Orden
INSERT INTO detalle_orden (orden_compra_id, producto_id, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 5, 5499.99, 27499.95),
(2, 2, 5, 4299.00, 21495.00),
(3, 3, 5, 6999.99, 34999.95),
(4, 4, 5, 9499.00, 47495.00),
(5, 9, 15, 459.00, 6885.00);

-- Kardex Movimientos
INSERT INTO kardex_movimiento (producto_id, tipo, cantidad, fecha, observacion) VALUES
(1, 'ENTRADA', 25, '2026-09-01 10:30:00', 'Recepción orden #1'),
(2, 'ENTRADA', 18, '2026-09-05 15:00:00', 'Recepción orden #2'),
(3, 'ENTRADA', 30, '2026-09-10 09:30:00', 'Recepción orden #3'),
(1, 'SALIDA', 3, '2026-09-12 11:00:00', 'Venta mostrador'),
(3, 'SALIDA', 5, '2026-09-14 14:00:00', 'Venta corporativa'),
(6, 'ENTRADA', 50, '2026-09-15 08:00:00', 'Reposición stock'),
(6, 'SALIDA', 10, '2026-09-18 16:30:00', 'Venta online');
