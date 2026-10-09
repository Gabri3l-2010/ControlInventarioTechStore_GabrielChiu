package org.gc.system.dao;

import java.util.List;

public interface GenericDAO<T, ID> {
    void insertar(T entity);
    void actualizar(T entity);
    void eliminar(ID id);
    List<T> listar();
    T buscarPorId(ID id);
}
