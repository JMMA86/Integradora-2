package com.mercadolibre.integradora2.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddOrderController implements Initializable {

    @FXML
    private Label date;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date.setText("Date: " + localDate.format(formatter));
    }
}
