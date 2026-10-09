package org.gc.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.gc.dao.ProductoDAO;
import org.gc.dao.impl.ProductoDAOImpl;
import org.gc.model.Producto;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductoController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private TextField txtUmbral;
    @FXML private TextField txtCategoriaId;
    @FXML private TextField txtProveedorId;
    @FXML private TableView<Producto> tblProductos;

    private ProductoDAO productoDAO = new ProductoDAOImpl();
    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
    private Producto productoSeleccionado = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTabla();
        tblProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                productoSeleccionado = newVal;
                txtNombre.setText(newVal.getNombre());
                txtPrecio.setText(String.valueOf(newVal.getPrecio()));
                txtStock.setText(String.valueOf(newVal.getStock()));
                txtUmbral.setText(String.valueOf(newVal.getUmbralMinimo()));
                txtCategoriaId.setText(String.valueOf(newVal.getCategoriaId()));
                txtProveedorId.setText(String.valueOf(newVal.getProveedorId()));
            }
        });
    }

    private void cargarTabla() {
        listaProductos.clear();
        listaProductos.addAll(productoDAO.listar());
        tblProductos.setItems(listaProductos);
    }

    @FXML
    private void handleInsertar() {
        try {
            Producto p = new Producto();
            p.setNombre(txtNombre.getText());
            p.setPrecio(Double.parseDouble(txtPrecio.getText()));
            p.setStock(Integer.parseInt(txtStock.getText()));
            p.setUmbralMinimo(Integer.parseInt(txtUmbral.getText()));
            p.setCategoriaId(Integer.parseInt(txtCategoriaId.getText()));
            p.setProveedorId(Integer.parseInt(txtProveedorId.getText()));
            productoDAO.insertar(p);
            cargarTabla();
            handleLimpiar();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Producto agregado exitosamente.");
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Verifique que los campos numéricos sean válidos.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al insertar: " + e.getMessage());
        }
    }

    @FXML
    private void handleActualizar() {
        if (productoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un producto de la tabla.");
            return;
        }
        try {
            productoSeleccionado.setNombre(txtNombre.getText());
            productoSeleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));
            productoSeleccionado.setStock(Integer.parseInt(txtStock.getText()));
            productoSeleccionado.setUmbralMinimo(Integer.parseInt(txtUmbral.getText()));
            productoSeleccionado.setCategoriaId(Integer.parseInt(txtCategoriaId.getText()));
            productoSeleccionado.setProveedorId(Integer.parseInt(txtProveedorId.getText()));
            productoDAO.actualizar(productoSeleccionado);
            cargarTabla();
            handleLimpiar();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Producto actualizado exitosamente.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al actualizar: " + e.getMessage());
        }
    }

    @FXML
    private void handleEliminar() {
        if (productoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un producto de la tabla.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de eliminar este producto?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                productoDAO.eliminar(productoSeleccionado.getId());
                cargarTabla();
                handleLimpiar();
            }
        });
    }

    @FXML
    private void handleLimpiar() {
        txtNombre.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtUmbral.clear();
        txtCategoriaId.clear();
        txtProveedorId.clear();
        productoSeleccionado = null;
        tblProductos.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo, mensaje);
        alerta.showAndWait();
    }
}
