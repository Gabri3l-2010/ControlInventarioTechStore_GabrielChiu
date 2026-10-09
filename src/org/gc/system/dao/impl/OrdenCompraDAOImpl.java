package org.gc.system.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.gc.system.dao.OrdenCompraDAO;
import org.gc.system.model.OrdenCompra;
import org.gc.system.util.Conexion;

public class OrdenCompraDAOImpl implements OrdenCompraDAO {
    @Override
    public void insertar(OrdenCompra entity) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_insertar_orden_compra(?, ?, ?, ?)}")) {
            cs.setInt(1, entity.getProveedorId());
            cs.setTimestamp(2, entity.getFecha());
            cs.setString(3, entity.getEstado());
            cs.setDouble(4, entity.getTotal());
            cs.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar orden de compra", e);
        }
    }

    @Override
    public void actualizar(OrdenCompra entity) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_actualizar_orden_compra(?, ?, ?, ?, ?)}")) {
            cs.setInt(1, entity.getId());
            cs.setInt(2, entity.getProveedorId());
            cs.setTimestamp(3, entity.getFecha());
            cs.setString(4, entity.getEstado());
            cs.setDouble(5, entity.getTotal());
            cs.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar orden de compra", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_eliminar_orden_compra(?)}")) {
            cs.setInt(1, id);
            cs.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar orden de compra", e);
        }
    }

    @Override
    public List<OrdenCompra> listar() {
        List<OrdenCompra> lista = new ArrayList<>();
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_listar_ordenes_compra()}");
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(new OrdenCompra(
                    rs.getInt("id"),
                    rs.getInt("proveedor_id"),
                    rs.getTimestamp("fecha"),
                    rs.getString("estado"),
                    rs.getDouble("total")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar ordenes de compra", e);
        }
        return lista;
    }

    @Override
    public OrdenCompra buscarPorId(Integer id) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_buscar_orden_compra_id(?)}")) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return new OrdenCompra(
                        rs.getInt("id"),
                        rs.getInt("proveedor_id"),
                        rs.getTimestamp("fecha"),
                        rs.getString("estado"),
                        rs.getDouble("total")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar orden de compra", e);
        }
        return null;
    }
}
