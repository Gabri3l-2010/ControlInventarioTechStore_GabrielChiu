package org.gc.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.gc.dao.DetalleOrdenDAO;
import org.gc.model.DetalleOrden;
import org.gc.util.Conexion;

public class DetalleOrdenDAOImpl implements DetalleOrdenDAO {

    @Override
    public void insertar(DetalleOrden entity) {
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_insertar_detalle_orden(?, ?, ?, ?, ?)}");
            cs.setInt(1, entity.getOrdenCompraId());
            cs.setInt(2, entity.getProductoId());
            cs.setInt(3, entity.getCantidad());
            cs.setDouble(4, entity.getPrecioUnitario());
            cs.setDouble(5, entity.getSubtotal());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar detalle de orden", e);
        }
    }

    @Override
    public void actualizar(DetalleOrden entity) {
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_actualizar_detalle_orden(?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, entity.getId());
            cs.setInt(2, entity.getOrdenCompraId());
            cs.setInt(3, entity.getProductoId());
            cs.setInt(4, entity.getCantidad());
            cs.setDouble(5, entity.getPrecioUnitario());
            cs.setDouble(6, entity.getSubtotal());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar detalle de orden", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_eliminar_detalle_orden(?)}");
            cs.setInt(1, id);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar detalle de orden", e);
        }
    }

    @Override
    public List<DetalleOrden> listar() {
        List<DetalleOrden> lista = new ArrayList<>();
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_listar_detalles_orden()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                lista.add(new DetalleOrden(
                    rs.getInt("id"),
                    rs.getInt("orden_compra_id"),
                    rs.getInt("producto_id"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario"),
                    rs.getDouble("subtotal")
                ));
            }
            rs.close();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar detalles de orden", e);
        }
        return lista;
    }

    @Override
    public DetalleOrden buscarPorId(Integer id) {
        DetalleOrden d = null;
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_buscar_detalle_orden_id(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                d = new DetalleOrden(rs.getInt("id"), rs.getInt("orden_compra_id"), rs.getInt("producto_id"), rs.getInt("cantidad"), rs.getDouble("precio_unitario"), rs.getDouble("subtotal"));
            }
            rs.close();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar detalle de orden", e);
        }
        return d;
    }
}
