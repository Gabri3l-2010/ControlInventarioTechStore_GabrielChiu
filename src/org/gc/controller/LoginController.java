package org.gc.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.gc.util.Conexion;
import org.gc.util.SessionContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    @FXML
    private void handleLogin() {
        String usuario = txtUsuario.getText().trim();
        String pass = txtPassword.getText().trim();

        if (usuario.isEmpty() || pass.isEmpty()) {
            lblError.setText("Ingrese sus credenciales");
            return;
        }

        try {
            Connection con = Conexion.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{call sp_autenticar_usuario(?, ?)}");
            cs.setString(1, usuario);
            cs.setString(2, pass);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                String rol = rs.getString("rol");
                SessionContext.getInstance().iniciarSesion(usuario, rol);

                // Cargar vista principal de productos
                Parent root = FXMLLoader.load(getClass().getResource("/org/gc/view/ProductoView.fxml"));
                Stage stage = (Stage) txtUsuario.getScene().getWindow();
                stage.setTitle("TechStore - Panel de " + rol);
                stage.setScene(new Scene(root, 750, 500));
                stage.setResizable(true);
                stage.show();
            } else {
                lblError.setText("Usuario o clave incorrectos");
            }

            rs.close();
            cs.close();
        } catch (Exception e) {
            lblError.setText("Error de conexión a BD");
            e.printStackTrace();
        }
    }
}
