package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import com.mercadolibre.integradora2.exception.DuplicatedElementException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddProductController {
    @FXML
    private TextField nameField, priceField, amountField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ToggleGroup categoryGroup;
    @FXML
    private RadioButton books, food, care, electronics, stationery, games, clothes, sports;
    @FXML
    private Button addProductBtn;

    private int category = -1;
    @FXML
    public void setCategory(ActionEvent actionEvent) {
        if (books.isSelected()) {
            category = 1;
        } else if (electronics.isSelected()) {
            category = 2;
        } else if (clothes.isSelected()) {
            category = 3;
        } else if (food.isSelected()) {
            category = 4;
        } else if (stationery.isSelected()) {
            category = 5;
        } else if (sports.isSelected()) {
            category = 6;
        } else if (care.isSelected()) {
            category = 7;
        } else if (games.isSelected()) {
            category = 8;
        }
        updateAddBtn();
    }

    @FXML
    public void AddProduct() {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int amount = Integer.parseInt(amountField.getText());
            String description = descriptionArea.getText();
            int category = this.category;
            MainApplication.getManager().addProduct(name, description, price, amount, category, 0);
            MainApplication.showAlert("Done", "Product added correctly.", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) addProductBtn.getScene().getWindow();
            stage.close();
            MainApplication.renderView("management-view.fxml", "Management System", 720, 480);
        } catch (IllegalArgumentException e) {
            MainApplication.showAlert("Error", "Please, make sure all inputs are correct.", Alert.AlertType.ERROR);
        } catch (DuplicatedElementException e) {
            MainApplication.showAlert("Error", "This product already exists.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void updateAddBtn() {
        addProductBtn.setDisable(nameField.getText().isEmpty() || priceField.getText().isEmpty() || amountField.getText().isEmpty() || descriptionArea.getText().isEmpty() || category == -1);
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) addProductBtn.getScene().getWindow();
        stage.close();
        MainApplication.renderView("management-view.fxml", "Management System", 720, 480);
    }
}
