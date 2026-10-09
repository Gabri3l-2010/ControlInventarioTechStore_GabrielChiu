package org.gc.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.gc.dao.CategoriaDAO;
import org.gc.model.Categoria;
import org.gc.util.Conexion;

public class CategoriaDAOImpl implements CategoriaDAO {

    @Override
    public void insertar(Categoria entity) {
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_insertar_categoria(?)}");
            cs.setString(1, entity.getNombre());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar categoria", e);
        }
    }

    @Override
    public void actualizar(Categoria entity) {
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_actualizar_categoria(?, ?)}");
            cs.setInt(1, entity.getId());
            cs.setString(2, entity.getNombre());
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar categoria", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_eliminar_categoria(?)}");
            cs.setInt(1, id);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar categoria", e);
        }
    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_listar_categorias()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("id"), rs.getString("nombre")));
            }
            rs.close();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar categorias", e);
        }
        return lista;
    }

    @Override
    public Categoria buscarPorId(Integer id) {
        Categoria c = null;
        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_buscar_categoria_id(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                c = new Categoria(rs.getInt("id"), rs.getString("nombre"));
            }
            rs.close();
            cs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar categoria", e);
        }
        return c;
    }
}
