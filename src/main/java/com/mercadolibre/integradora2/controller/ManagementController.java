package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManagementController implements Initializable {
    private boolean saved;

    @FXML
    private Button addProductBtn;

    @FXML
    private Button addOrderBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private TableView productsTable;

    @FXML
    private TableView ordersTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saved = true;
        Scene scene = addProductBtn.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.setOnCloseRequest(event -> {
            if (!saved) {
                Optional<ButtonType> result = MainApplication.showAlert("Caution", "Do you want to save your changes?", Alert.AlertType.CONFIRMATION);
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    MainApplication.getManager().writeData();
                }
            }
        });
    }
}
