package org.gc.system.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.gc.system.dao.ProductoDAO;
import org.gc.system.model.Producto;
import org.gc.system.util.Conexion;
import org.gc.system.exceptions.DBException;

public class ProductoDAOImpl implements ProductoDAO {
    @Override
    public void insertar(Producto entity) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_insertar_producto(?, ?, ?, ?, ?, ?)}")) {
            cs.setString(1, entity.getNombre());
            cs.setDouble(2, entity.getPrecio());
            cs.setInt(3, entity.getStock());
            cs.setInt(4, entity.getUmbralMinimo());
            cs.setInt(5, entity.getCategoriaId());
            cs.setInt(6, entity.getProveedorId());
            cs.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar producto", e);
        }
    }

    @Override
    public void actualizar(Producto entity) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_actualizar_producto(?, ?, ?, ?, ?, ?, ?)}")) {
            cs.setInt(1, entity.getId());
            cs.setString(2, entity.getNombre());
            cs.setDouble(3, entity.getPrecio());
            cs.setInt(4, entity.getStock());
            cs.setInt(5, entity.getUmbralMinimo());
            cs.setInt(6, entity.getCategoriaId());
            cs.setInt(7, entity.getProveedorId());
            cs.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_eliminar_producto(?)}")) {
            cs.setInt(1, id);
            cs.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }

    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_listar_productos()}");
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getInt("umbral_minimo"),
                    rs.getInt("categoria_id"),
                    rs.getInt("proveedor_id")
                );
                lista.add(p);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar productos", e);
        }
        return lista;
    }

    @Override
    public Producto buscarPorId(Integer id) {
        Producto p = null;
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_buscar_producto_id(?)}")) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    p = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getInt("umbral_minimo"),
                        rs.getInt("categoria_id"),
                        rs.getInt("proveedor_id")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar producto", e);
        }
        return p;
    }
}
