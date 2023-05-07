package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import com.mercadolibre.integradora2.model.Order;
import com.mercadolibre.integradora2.model.Product;
import com.mercadolibre.integradora2.model.ProductCategory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ManagementController implements Initializable {
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
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Integer> amountColumn;
    @FXML
    private TableColumn<Product, ProductCategory> categoryColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> customerColumn;
    @FXML
    private TableColumn<Order, Double> purchaseColumn;
    @FXML
    private TableColumn<Order, String> dateColumn;

    @FXML
    public void onSaveBtn() {
        MainApplication.getManager().writeData();
        MainApplication.showAlert("Log info", "Data saved correctly.", Alert.AlertType.INFORMATION);
    }
    @FXML
    public void onAddProductBtn() {
        MainApplication.renderView("addProduct-view.fxml", "Add product", 520, 360);
        Stage stage = (Stage) addStockBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void onStockBtn() {
        MainApplication.renderView("addStock-view.fxml", "Add Stock", 360, 240);
        Stage stage = (Stage) addStockBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void onAddOrderBtn() {
        MainApplication.renderView("addOrder-view.fxml", "Add Order", 400, 500);
        Stage stage = (Stage) addStockBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onSearchBtn() {
        MainApplication.renderView("search-view.fxml", "Add Order",400, 550);
        Stage stage = (Stage) addStockBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initialize products table
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTable.setItems(FXCollections.observableArrayList(MainApplication.getManager().getProducts()));
        //Initialize orders table
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        purchaseColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("DATE"));
        ordersTable.setItems(FXCollections.observableArrayList(MainApplication.getManager().getOrders()));
    }
}
