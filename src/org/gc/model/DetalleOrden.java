package org.gc.model;

public class DetalleOrden {
    private Integer id;
    private Integer ordenCompraId;
    private Integer productoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

    public DetalleOrden() {}

    public DetalleOrden(Integer id, Integer ordenCompraId, Integer productoId, Integer cantidad, Double precioUnitario, Double subtotal) {
        this.id = id;
        this.ordenCompraId = ordenCompraId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getOrdenCompraId() { return ordenCompraId; }
    public void setOrdenCompraId(Integer ordenCompraId) { this.ordenCompraId = ordenCompraId; }
    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() { return "Detalle #" + id; }
}
