package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import com.mercadolibre.integradora2.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class AddStockController implements Initializable {
    @FXML
    private Spinner<Integer> stockNumber;
    @FXML
    private Button addStockBtn;
    @FXML
    private TextArea productInfo;
    @FXML
    private TextField productField;
    @FXML
    private Button searchBtn;

    private Product product;

    @FXML
    public void searchProduct() {
        try {
            Product product = MainApplication.getManager().searchProduct(productField.getText());
            this.product = product;
            productInfo.setText(product.toString());
        } catch (NoSuchElementException e) {
            MainApplication.showAlert("Error", "Product not found.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void addStock() {
        if (product == null) {
            MainApplication.showAlert("Error", "Product not selected", Alert.AlertType.ERROR);
        } else {
            product.setAmount(product.getAmount() + stockNumber.getValue());
            MainApplication.showAlert("Log info", "Stock changed successfully", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) addStockBtn.getScene().getWindow();
            stage.close();
            MainApplication.renderView("management-view.fxml", "Management System", 720, 480);
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) addStockBtn.getScene().getWindow();
        stage.close();
        MainApplication.renderView("management-view.fxml", "Management System", 720, 480);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(1);
        stockNumber.setValueFactory(valueFactory);
    }
}
