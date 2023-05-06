package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ManagementController {
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn descriptionColumn;
    @FXML
    private Button addStockBtn;
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
    public void onSaveBtn() {
        MainApplication.getManager().writeData();
        MainApplication.showAlert("Log info", "Data saved correctly.", Alert.AlertType.INFORMATION);
    }
    @FXML
    public void onAddProductBtn() {
        MainApplication.renderView("addProduct-view.fxml", "Add product", 520, 360);
    }
    @FXML
    public void onStockBtn() {
        MainApplication.renderView("addStock-view.fxml", "Add Stock", 360, 240);
    }
}
