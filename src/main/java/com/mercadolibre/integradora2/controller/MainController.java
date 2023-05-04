package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import com.mercadolibre.integradora2.model.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {
    private final Manager manager = new Manager();
    @FXML
    private Label welcomeText;
    @FXML
    private Button startSystemBtn;

    @FXML
    protected void onStartBtn() {
        Stage stage = (Stage) startSystemBtn.getScene().getWindow();
        stage.close();
        String msj = manager.readData();
        if (msj.equals("Files loaded successfully.")) {
            MainApplication.showAlert("Log info", msj, Alert.AlertType.INFORMATION);
        }
        MainApplication.renderView("management-view.fxml", 720, 480);
    }
}