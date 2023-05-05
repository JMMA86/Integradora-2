package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagementController implements Initializable {
    @FXML
    public TableColumn nameColumn;
    @FXML
    public TableColumn descriptionColumn;
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

    public void onAddProductBtn() {
        MainApplication.renderView("addProduct-view.fxml", "Add product", 640, 360);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
