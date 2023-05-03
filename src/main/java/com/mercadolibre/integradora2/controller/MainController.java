package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.model.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    private final Manager manager = new Manager();
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(manager.readData());
        manager.writeData();
    }
}