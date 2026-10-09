package org.gc.system.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.gc.system.dao.ProveedorDAO;
import org.gc.system.model.Proveedor;
import org.gc.system.util.Conexion;

public class ProveedorDAOImpl implements ProveedorDAO {
    @Override
    public void insertar(Proveedor entity) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_insertar_proveedor(?, ?, ?)}")) {
            cs.setString(1, entity.getNombre());
            cs.setString(2, entity.getTelefono());
            cs.setString(3, entity.getEmail());
            cs.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar proveedor", e);
        }
    }

    @Override
    public void actualizar(Proveedor entity) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_actualizar_proveedor(?, ?, ?, ?)}")) {
            cs.setInt(1, entity.getId());
            cs.setString(2, entity.getNombre());
            cs.setString(3, entity.getTelefono());
            cs.setString(4, entity.getEmail());
            cs.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar proveedor", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_eliminar_proveedor(?)}")) {
            cs.setInt(1, id);
            cs.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar proveedor", e);
        }
    }

    @Override
    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_listar_proveedores()}");
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(new Proveedor(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("email")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar proveedores", e);
        }
        return lista;
    }

    @Override
    public Proveedor buscarPorId(Integer id) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_buscar_proveedor_id(?)}")) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return new Proveedor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar proveedor", e);
        }
        return null;
    }
}
