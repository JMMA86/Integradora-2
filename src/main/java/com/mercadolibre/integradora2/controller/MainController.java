package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.model.Manager;
import com.mercadolibre.integradora2.model.Product;
import com.mercadolibre.integradora2.model.ProductCategory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Arrays;

public class MainController {
    private final Manager manager = new Manager();
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        manager.addProduct("Cocacola","Hola",  112, 2, 2, 3);
        manager.addProduct("Cocacola5","Hola",  112, 1, 2, 3);
        manager.addProduct("PonyMalta","Hola",  112, 1, 2, 3);
        manager.addProduct("Cocacola5","Hola",  112, 1, 2, 3);
        manager.addProduct("Cocacola2","Hola",  113, 2, 1, 2);
        manager.addProduct("Cocacola3","Hola",  111, 3, 3, 1);
        manager.addProduct("Cocacola6","Hola",  111, 2, 3, 5);
        manager.addProduct("Cocacola7","Hola",  111, 4, 3, 0);
        manager.addProduct("Cocacola4","Hola",  115, 6, 4, 4);
        Product[] result = manager.searchProductsByStrings("lta", "ola6", true);
        for (Product mx : result) {
            System.out.println(mx.getName());
        }
//        welcomeText.setText(manager.readData());
//        manager.writeData();
    }
}