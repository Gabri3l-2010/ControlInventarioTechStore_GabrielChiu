package org.gc.system.model;

import java.sql.Timestamp;

public class OrdenCompra {
    private Integer id;
    private Integer proveedorId;
    private Timestamp fecha;
    private String estado;
    private Double total;

    public OrdenCompra() {}

    public OrdenCompra(Integer id, Integer proveedorId, Timestamp fecha, String estado, Double total) {
        this.id = id;
        this.proveedorId = proveedorId;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getProveedorId() { return proveedorId; }
    public void setProveedorId(Integer proveedorId) { this.proveedorId = proveedorId; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    @Override
    public String toString() { return "Orden #" + id + " - " + estado; }
}
