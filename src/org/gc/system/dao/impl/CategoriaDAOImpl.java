package org.gc.system.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.gc.system.dao.CategoriaDAO;
import org.gc.system.model.Categoria;
import org.gc.system.util.Conexion;

public class CategoriaDAOImpl implements CategoriaDAO {
    @Override
    public void insertar(Categoria entity) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_insertar_categoria(?)}")) {
            cs.setString(1, entity.getNombre());
            cs.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public void actualizar(Categoria entity) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_actualizar_categoria(?, ?)}")) {
            cs.setInt(1, entity.getId());
            cs.setString(2, entity.getNombre());
            cs.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_eliminar_categoria(?)}")) {
            cs.setInt(1, id);
            cs.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_listar_categorias()}");
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return lista;
    }

    @Override
    public Categoria buscarPorId(Integer id) {
        try (Connection con = Conexion.getInstance().getConnection();
             CallableStatement cs = con.prepareCall("{call sp_buscar_categoria_id(?)}")) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) return new Categoria(rs.getInt("id"), rs.getString("nombre"));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return null;
    }
}
