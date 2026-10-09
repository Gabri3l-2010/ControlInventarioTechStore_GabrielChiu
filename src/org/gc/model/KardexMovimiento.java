package org.gc.model;

import java.sql.Timestamp;

public class KardexMovimiento {
    private Integer id;
    private Integer productoId;
    private String tipo;
    private Integer cantidad;
    private Timestamp fecha;
    private String observacion;

    public KardexMovimiento() {}

    public KardexMovimiento(Integer id, Integer productoId, String tipo, Integer cantidad, Timestamp fecha, String observacion) {
        this.id = id;
        this.productoId = productoId;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    @Override
    public String toString() { return tipo + " x" + cantidad; }
}
