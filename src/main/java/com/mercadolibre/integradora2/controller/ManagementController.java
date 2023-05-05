package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import com.mercadolibre.integradora2.model.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ManagementController {

    @FXML
    private Button addProductBtn;

    @FXML
    private Button addOrderBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private TableView<String> productsTable;

    @FXML
    private TableView<String> ordersTable;

    @FXML
    protected void onSaveBtn() {
        MainApplication.getManager().writeData();
        MainApplication.showAlert("Log info", "Data saved correctly.", Alert.AlertType.INFORMATION);
    }
}
