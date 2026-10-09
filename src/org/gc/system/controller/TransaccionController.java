package org.gc.system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.gc.system.dao.ProductoDAO;
import org.gc.system.dao.KardexMovimientoDAO;
import org.gc.system.dao.OrdenCompraDAO;
import org.gc.system.dao.impl.ProductoDAOImpl;
import org.gc.system.dao.impl.KardexMovimientoDAOImpl;
import org.gc.system.dao.impl.OrdenCompraDAOImpl;
import org.gc.system.model.Producto;
import org.gc.system.model.KardexMovimiento;
import org.gc.system.model.OrdenCompra;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class TransaccionController implements Initializable {

    @FXML private TextField txtProductoId;
    @FXML private TextField txtCantidad;
    @FXML private Label lblProductoInfo;
    @FXML private Label lblMensaje;
    @FXML private TableView<KardexMovimiento> tblKardex;
    @FXML private TableView<OrdenCompra> tblOrdenes;

    private ProductoDAO productoDAO = new ProductoDAOImpl();
    private KardexMovimientoDAO kardexDAO = new KardexMovimientoDAOImpl();
    private OrdenCompraDAO ordenDAO = new OrdenCompraDAOImpl();

    private ObservableList<KardexMovimiento> listaKardex = FXCollections.observableArrayList();
    private ObservableList<OrdenCompra> listaOrdenes = FXCollections.observableArrayList();

    private Producto productoActual = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarKardex();
        cargarOrdenes();
    }

    @FXML
    private void handleBuscar() {
        try {
            int id = Integer.parseInt(txtProductoId.getText());
            productoActual = productoDAO.buscarPorId(id);
            if (productoActual != null) {
                lblProductoInfo.setText(productoActual.getNombre()
                        + " | Precio: Q" + productoActual.getPrecio()
                        + " | Stock: " + productoActual.getStock()
                        + " | Umbral mín: " + productoActual.getUmbralMinimo());
                lblMensaje.setText("");
            } else {
                lblProductoInfo.setText("Producto no encontrado.");
                productoActual = null;
            }
        } catch (NumberFormatException e) {
            lblProductoInfo.setText("Ingrese un ID numérico válido.");
        }
    }

    @FXML
    private void handleVender() {
        if (productoActual == null) {
            lblMensaje.setText("Primero busque un producto.");
            lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad <= 0) {
                lblMensaje.setText("La cantidad debe ser mayor a 0.");
                lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
                return;
            }
            if (cantidad > productoActual.getStock()) {
                lblMensaje.setText("Stock insuficiente. Disponible: " + productoActual.getStock());
                lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
                return;
            }

            // Decrementar stock
            int nuevoStock = productoActual.getStock() - cantidad;
            productoActual.setStock(nuevoStock);
            productoDAO.actualizar(productoActual);

            // Registrar en Kardex
            KardexMovimiento mov = new KardexMovimiento();
            mov.setProductoId(productoActual.getId());
            mov.setTipo("SALIDA");
            mov.setCantidad(cantidad);
            mov.setFecha(new Timestamp(System.currentTimeMillis()));
            mov.setObservacion("Venta registrada");
            kardexDAO.insertar(mov);

            lblMensaje.setTextFill(javafx.scene.paint.Color.GREEN);
            lblMensaje.setText("Venta exitosa. Nuevo stock: " + nuevoStock);

            // Verificar umbral mínimo y generar orden de compra automática
            if (nuevoStock < productoActual.getUmbralMinimo()) {
                OrdenCompra orden = new OrdenCompra();
                orden.setProveedorId(productoActual.getProveedorId());
                orden.setFecha(new Timestamp(System.currentTimeMillis()));
                orden.setEstado("Pendiente");
                int cantidadReponer = productoActual.getUmbralMinimo() * 2 - nuevoStock;
                orden.setTotal(cantidadReponer * productoActual.getPrecio());
                ordenDAO.insertar(orden);

                lblMensaje.setTextFill(javafx.scene.paint.Color.ORANGE);
                lblMensaje.setText("Venta exitosa. ALERTA: Stock bajo (" + nuevoStock
                        + "). Se generó Orden de Compra automática por " + cantidadReponer + " unidades.");
            }

            // Refrescar todo
            handleBuscar();
            cargarKardex();
            cargarOrdenes();
            txtCantidad.clear();

        } catch (NumberFormatException e) {
            lblMensaje.setText("Ingrese una cantidad numérica válida.");
            lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
        } catch (Exception e) {
            lblMensaje.setText("Error: " + e.getMessage());
            lblMensaje.setTextFill(javafx.scene.paint.Color.RED);
            e.printStackTrace();
        }
    }

    private void cargarKardex() {
        listaKardex.clear();
        listaKardex.addAll(kardexDAO.listar());
        tblKardex.setItems(listaKardex);
    }

    private void cargarOrdenes() {
        listaOrdenes.clear();
        listaOrdenes.addAll(ordenDAO.listar());
        tblOrdenes.setItems(listaOrdenes);
    }
}
