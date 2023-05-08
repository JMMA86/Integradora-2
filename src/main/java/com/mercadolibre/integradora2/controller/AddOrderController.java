package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import com.mercadolibre.integradora2.exception.EmptyOrderException;
import com.mercadolibre.integradora2.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class AddOrderController implements Initializable {

    @FXML
    private TextField customerName;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    public TableColumn<Product, String> nameColumn;
    @FXML
    public TableColumn<Product, Integer> amountColumn;
    @FXML
    public TableColumn<Locale.Category, String> categoryColumn;
    @FXML
    public TableColumn<Product, Double> priceColumn;
    @FXML
    private Label totalPrice;
    @FXML
    private TextField productName;
    @FXML
    private Button searchProduct;
    @FXML
    private TextArea productInfo;
    @FXML
    private Spinner<Integer> amountProduct;
    @FXML
    private Button addAmount;
    @FXML
    private Button createOrderBtn;
    @FXML
    private Label date;
    private Product product;
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private int totalPriceAccumulated = 0;

    @FXML
    public void searchProduct() {
        try {
            Product product = MainApplication.getManager().searchProduct(productName.getText());
            this.product = product;
            productInfo.setText(product.toString());
            amountProduct.setDisable(false);
            addAmount.setDisable(false);
        } catch (NoSuchElementException e) {
            MainApplication.showAlert("Error", "Product not found.", Alert.AlertType.ERROR);
        }
    }
    @FXML
    public void addProduct() {
        if (amountProduct.getValue() > this.product.getAmount()) {
            MainApplication.showAlert("Error", "Amount limit exceeded.", Alert.AlertType.ERROR);
        } else if (searchAddedProduct(this.product)) {
            MainApplication.showAlert("Error", "Product already added.", Alert.AlertType.ERROR);
        } else if (this.product.getAmount() == 0) {
            MainApplication.showAlert("Error", "This product has no stock.", Alert.AlertType.ERROR);
        } else {
            MainApplication.showAlert("Log info", "Added correctly", Alert.AlertType.INFORMATION);
            //Set new stock
            this.product.setAmount(this.product.getAmount() - amountProduct.getValue());
            this.product.setTimesBought(this.product.getTimesBought() + amountProduct.getValue());
            products.add(new Product(this.product.getName(), this.product.getDescription(), this.product.getPrice() * amountProduct.getValue(), amountProduct.getValue(), this.product.getCategory(), this.product.getTimesBought()));
            productsTable.setItems(products);
            //Set new totalPrice
            totalPriceAccumulated += this.product.getPrice() * amountProduct.getValue();
            totalPrice.setText("Total: " + totalPriceAccumulated);
        }
    }

    public boolean searchAddedProduct(Product product) {
        for (Product value : products) {
            if (value == product) {
                return true;
            }
        }
        return false;
    }

    public void addOrder() throws EmptyOrderException {
        if (customerName.getText().isEmpty()) {
            MainApplication.showAlert("Error", "Please, enter customer name.", Alert.AlertType.ERROR);
        } else if (products.isEmpty()) {
            MainApplication.showAlert("Error", "Order is empty.", Alert.AlertType.ERROR);
        } else {
            MainApplication.showAlert("Log info", "Order added correctly.", Alert.AlertType.INFORMATION);
            MainApplication.getManager().addOrder(customerName.getText(), new ArrayList<>(products));
            Stage stage = (Stage) createOrderBtn.getScene().getWindow();
            stage.close();
            MainApplication.renderView("management-view.fxml", "Management System", 720, 480);
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) createOrderBtn.getScene().getWindow();
        stage.close();
        MainApplication.renderView("management-view.fxml", "Management System", 720, 480);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initialize columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //Initialize date
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date.setText("Date: " + localDate.format(formatter));
        //Initialize Spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(1);
        amountProduct.setValueFactory(valueFactory);

    }
}
