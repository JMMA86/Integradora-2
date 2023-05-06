package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private Button startSystemBtn;

    @FXML
    public void onStartBtn() {
        Stage stage = (Stage) startSystemBtn.getScene().getWindow();
        stage.close();
        String msj = MainApplication.getManager().readData();
        if (!msj.equals("Files loaded successfully.")) {
            MainApplication.showAlert("Log info", msj, Alert.AlertType.INFORMATION);
        }
        MainApplication.renderView("management-view.fxml", "Management System", 720, 480);
    }
}