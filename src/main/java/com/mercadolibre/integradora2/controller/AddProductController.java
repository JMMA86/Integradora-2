package com.mercadolibre.integradora2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddProductController {
    @FXML
    public TextField nameField, priceField, amountField;
    @FXML
    public TextArea descriptionArea;
    @FXML
    public ToggleGroup categoryGroup;
    @FXML
    public RadioButton books, food, care, electronics, stationery, games, clothes, sports;
    @FXML
    public Button addProductBtn;

    public void getCategory(ActionEvent actionEvent) {
        addProductBtn.setDisable(false);
    }

    public void AddProduct(ActionEvent actionEvent) {

    }
}
