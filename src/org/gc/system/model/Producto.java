package org.gc.system.model;

public class Producto {
    private Integer id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private Integer umbralMinimo;
    private Integer categoriaId;
    private Integer proveedorId;

    public Producto() {}

    public Producto(Integer id, String nombre, Double precio, Integer stock, Integer umbralMinimo, Integer categoriaId, Integer proveedorId) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.umbralMinimo = umbralMinimo;
        this.categoriaId = categoriaId;
        this.proveedorId = proveedorId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public Integer getUmbralMinimo() { return umbralMinimo; }
    public void setUmbralMinimo(Integer umbralMinimo) { this.umbralMinimo = umbralMinimo; }
    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }
    public Integer getProveedorId() { return proveedorId; }
    public void setProveedorId(Integer proveedorId) { this.proveedorId = proveedorId; }

    @Override
    public String toString() { return nombre; }
}
