-- =====================================================
-- Stored Procedures CRUD - TechStore (MySQL 8.0)
-- =====================================================
USE techstore;

-- ==================== USUARIO ====================
DELIMITER //
CREATE PROCEDURE sp_autenticar_usuario(IN p_username VARCHAR(50), IN p_password VARCHAR(255))
BEGIN
    SELECT id, username, rol FROM usuario WHERE username = p_username AND password = p_password AND activo = 1;
END //
DELIMITER ;

-- ==================== CATEGORIA ====================
DELIMITER //
CREATE PROCEDURE sp_insertar_categoria(IN p_nombre VARCHAR(100))
BEGIN
    INSERT INTO categoria(nombre) VALUES (p_nombre);
END //

CREATE PROCEDURE sp_actualizar_categoria(IN p_id INT, IN p_nombre VARCHAR(100))
BEGIN
    UPDATE categoria SET nombre = p_nombre WHERE id = p_id;
END //

CREATE PROCEDURE sp_eliminar_categoria(IN p_id INT)
BEGIN
    DELETE FROM categoria WHERE id = p_id;
END //

CREATE PROCEDURE sp_listar_categorias()
BEGIN
    SELECT * FROM categoria ORDER BY id;
END //

CREATE PROCEDURE sp_buscar_categoria_id(IN p_id INT)
BEGIN
    SELECT * FROM categoria WHERE id = p_id;
END //
DELIMITER ;

-- ==================== PROVEEDOR ====================
DELIMITER //
CREATE PROCEDURE sp_insertar_proveedor(IN p_nombre VARCHAR(150), IN p_telefono VARCHAR(20), IN p_email VARCHAR(100))
BEGIN
    INSERT INTO proveedor(nombre, telefono, email) VALUES (p_nombre, p_telefono, p_email);
END //

CREATE PROCEDURE sp_actualizar_proveedor(IN p_id INT, IN p_nombre VARCHAR(150), IN p_telefono VARCHAR(20), IN p_email VARCHAR(100))
BEGIN
    UPDATE proveedor SET nombre = p_nombre, telefono = p_telefono, email = p_email WHERE id = p_id;
END //

CREATE PROCEDURE sp_eliminar_proveedor(IN p_id INT)
BEGIN
    DELETE FROM proveedor WHERE id = p_id;
END //

CREATE PROCEDURE sp_listar_proveedores()
BEGIN
    SELECT * FROM proveedor ORDER BY id;
END //

CREATE PROCEDURE sp_buscar_proveedor_id(IN p_id INT)
BEGIN
    SELECT * FROM proveedor WHERE id = p_id;
END //
DELIMITER ;

-- ==================== PRODUCTO ====================
DELIMITER //
CREATE PROCEDURE sp_insertar_producto(IN p_nombre VARCHAR(150), IN p_precio DECIMAL(10,2), IN p_stock INT, IN p_umbral INT, IN p_cat_id INT, IN p_prov_id INT)
BEGIN
    INSERT INTO producto(nombre, precio, stock, umbral_minimo, categoria_id, proveedor_id)
    VALUES (p_nombre, p_precio, p_stock, p_umbral, p_cat_id, p_prov_id);
END //

CREATE PROCEDURE sp_actualizar_producto(IN p_id INT, IN p_nombre VARCHAR(150), IN p_precio DECIMAL(10,2), IN p_stock INT, IN p_umbral INT, IN p_cat_id INT, IN p_prov_id INT)
BEGIN
    UPDATE producto SET nombre = p_nombre, precio = p_precio, stock = p_stock, umbral_minimo = p_umbral, categoria_id = p_cat_id, proveedor_id = p_prov_id WHERE id = p_id;
END //

CREATE PROCEDURE sp_eliminar_producto(IN p_id INT)
BEGIN
    DELETE FROM producto WHERE id = p_id;
END //

CREATE PROCEDURE sp_listar_productos()
BEGIN
    SELECT * FROM producto ORDER BY id;
END //

CREATE PROCEDURE sp_buscar_producto_id(IN p_id INT)
BEGIN
    SELECT * FROM producto WHERE id = p_id;
END //
DELIMITER ;

-- ==================== ORDEN DE COMPRA ====================
DELIMITER //
CREATE PROCEDURE sp_insertar_orden_compra(IN p_prov_id INT, IN p_fecha TIMESTAMP, IN p_estado VARCHAR(20), IN p_total DECIMAL(12,2))
BEGIN
    INSERT INTO orden_compra(proveedor_id, fecha, estado, total) VALUES (p_prov_id, p_fecha, p_estado, p_total);
END //

CREATE PROCEDURE sp_actualizar_orden_compra(IN p_id INT, IN p_prov_id INT, IN p_fecha TIMESTAMP, IN p_estado VARCHAR(20), IN p_total DECIMAL(12,2))
BEGIN
    UPDATE orden_compra SET proveedor_id = p_prov_id, fecha = p_fecha, estado = p_estado, total = p_total WHERE id = p_id;
END //

CREATE PROCEDURE sp_eliminar_orden_compra(IN p_id INT)
BEGIN
    DELETE FROM orden_compra WHERE id = p_id;
END //

CREATE PROCEDURE sp_listar_ordenes_compra()
BEGIN
    SELECT * FROM orden_compra ORDER BY id;
END //

CREATE PROCEDURE sp_buscar_orden_compra_id(IN p_id INT)
BEGIN
    SELECT * FROM orden_compra WHERE id = p_id;
END //
DELIMITER ;

-- ==================== DETALLE ORDEN ====================
DELIMITER //
CREATE PROCEDURE sp_insertar_detalle_orden(IN p_orden_id INT, IN p_prod_id INT, IN p_cantidad INT, IN p_precio DECIMAL(10,2), IN p_subtotal DECIMAL(12,2))
BEGIN
    INSERT INTO detalle_orden(orden_compra_id, producto_id, cantidad, precio_unitario, subtotal)
    VALUES (p_orden_id, p_prod_id, p_cantidad, p_precio, p_subtotal);
END //

CREATE PROCEDURE sp_actualizar_detalle_orden(IN p_id INT, IN p_orden_id INT, IN p_prod_id INT, IN p_cantidad INT, IN p_precio DECIMAL(10,2), IN p_subtotal DECIMAL(12,2))
BEGIN
    UPDATE detalle_orden SET orden_compra_id = p_orden_id, producto_id = p_prod_id, cantidad = p_cantidad, precio_unitario = p_precio, subtotal = p_subtotal WHERE id = p_id;
END //

CREATE PROCEDURE sp_eliminar_detalle_orden(IN p_id INT)
BEGIN
    DELETE FROM detalle_orden WHERE id = p_id;
END //

CREATE PROCEDURE sp_listar_detalles_orden()
BEGIN
    SELECT * FROM detalle_orden ORDER BY id;
END //

CREATE PROCEDURE sp_buscar_detalle_orden_id(IN p_id INT)
BEGIN
    SELECT * FROM detalle_orden WHERE id = p_id;
END //
DELIMITER ;

-- ==================== KARDEX MOVIMIENTO ====================
DELIMITER //
CREATE PROCEDURE sp_insertar_kardex(IN p_prod_id INT, IN p_tipo VARCHAR(10), IN p_cantidad INT, IN p_fecha TIMESTAMP, IN p_obs VARCHAR(255))
BEGIN
    INSERT INTO kardex_movimiento(producto_id, tipo, cantidad, fecha, observacion)
    VALUES (p_prod_id, p_tipo, p_cantidad, p_fecha, p_obs);
END //

CREATE PROCEDURE sp_actualizar_kardex(IN p_id INT, IN p_prod_id INT, IN p_tipo VARCHAR(10), IN p_cantidad INT, IN p_fecha TIMESTAMP, IN p_obs VARCHAR(255))
BEGIN
    UPDATE kardex_movimiento SET producto_id = p_prod_id, tipo = p_tipo, cantidad = p_cantidad, fecha = p_fecha, observacion = p_obs WHERE id = p_id;
END //

CREATE PROCEDURE sp_eliminar_kardex(IN p_id INT)
BEGIN
    DELETE FROM kardex_movimiento WHERE id = p_id;
END //

CREATE PROCEDURE sp_listar_kardex()
BEGIN
    SELECT * FROM kardex_movimiento ORDER BY fecha DESC;
END //

CREATE PROCEDURE sp_buscar_kardex_id(IN p_id INT)
BEGIN
    SELECT * FROM kardex_movimiento WHERE id = p_id;
END //
DELIMITER ;
