package org.gc.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.gc.dao.KardexMovimientoDAO;
import org.gc.model.KardexMovimiento;
import org.gc.util.Conexion;

public class KardexMovimientoDAOImpl implements KardexMovimientoDAO {

    @Override
    public void insertar(KardexMovimiento entity) {
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_insertar_kardex(?, ?, ?, ?, ?)}");
            cs.setInt(1, entity.getProductoId());
            cs.setString(2, entity.getTipo());
            cs.setInt(3, entity.getCantidad());
            cs.setTimestamp(4, entity.getFecha());
            cs.setString(5, entity.getObservacion());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar movimiento kardex", e);
        }
    }

    @Override
    public void actualizar(KardexMovimiento entity) {
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_actualizar_kardex(?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, entity.getId());
            cs.setInt(2, entity.getProductoId());
            cs.setString(3, entity.getTipo());
            cs.setInt(4, entity.getCantidad());
            cs.setTimestamp(5, entity.getFecha());
            cs.setString(6, entity.getObservacion());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar movimiento kardex", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_eliminar_kardex(?)}");
            cs.setInt(1, id);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar movimiento kardex", e);
        }
    }

    @Override
    public List<KardexMovimiento> listar() {
        List<KardexMovimiento> lista = new ArrayList<>();
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_listar_kardex()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                lista.add(new KardexMovimiento(
                    rs.getInt("id"),
                    rs.getInt("producto_id"),
                    rs.getString("tipo"),
                    rs.getInt("cantidad"),
                    rs.getTimestamp("fecha"),
                    rs.getString("observacion")
                ));
            }
            rs.close();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar kardex", e);
        }
        return lista;
    }

    @Override
    public KardexMovimiento buscarPorId(Integer id) {
        KardexMovimiento k = null;
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_buscar_kardex_id(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                k = new KardexMovimiento(rs.getInt("id"), rs.getInt("producto_id"), rs.getString("tipo"), rs.getInt("cantidad"), rs.getTimestamp("fecha"), rs.getString("observacion"));
            }
            rs.close();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar kardex", e);
        }
        return k;
    }
}
