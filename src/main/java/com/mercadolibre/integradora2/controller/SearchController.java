package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import com.mercadolibre.integradora2.model.Product;
import com.mercadolibre.integradora2.model.ProductCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    //Selectors
    @FXML
    private ComboBox<String> searcherSelector;
    @FXML
    private ComboBox<String> orderSelector;
    //Minimums and Maximums
    @FXML
    private TextField lowerValueField;
    @FXML
    private TextField higherValueField;
    //Searcher
    @FXML
    private Button searchBtn;
    //RadioButtons for productSearch
    @FXML
    private RadioButton priceRB;
    @FXML
    private ToggleGroup searcherToggle;
    @FXML
    private RadioButton categoryRB;
    @FXML
    private RadioButton stockRB;
    @FXML
    private RadioButton prefixRB;
    @FXML
    private RadioButton timesRB;
    @FXML
    private RadioButton suffixRB;
    //Category selector
    @FXML
    private ComboBox<ProductCategory> categorySelector;
    //TableView for Products
    @FXML
    private TableView<Product> resultTable;
    @FXML
    private TableColumn<Product, String> nameProduct;
    @FXML
    private TableColumn<Product, Integer> stockProduct;
    @FXML
    private TableColumn<Product, ProductCategory> categoryProduct;
    @FXML
    private TableColumn<Product, Double> priceProduct;
    //Return Button
    @FXML
    private Button returnBtn;
    @FXML
    public void selectSearcher() {
    }
    @FXML
    public void selectOrder() {
    }
    @FXML
    public void updateSearchBtn() {
    }
    @FXML
    public void searchProduct() {
    }
    @FXML
    public void close() {
        Stage stage = (Stage) searchBtn.getScene().getWindow();
        stage.close();
        MainApplication.renderView("management-view.fxml", "Management System", 720, 480);
    }

    //Default values
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initialize product selector and ascending order (including all options)
        ObservableList<String> listSearcherSelector = FXCollections.observableArrayList("Product Searcher", "Order Searcher");
        searcherSelector.setItems(listSearcherSelector);
        searcherSelector.setValue(listSearcherSelector.get(0));
        ObservableList<String> listOrderSelector = FXCollections.observableArrayList("Ascending Order", "Descending Order");
        orderSelector.setItems(listOrderSelector);
        orderSelector.setValue(listOrderSelector.get(0));
        //Initialize category options
        ObservableList<ProductCategory> listCategorySelector = FXCollections.observableArrayList(ProductCategory.values());
        categorySelector.setItems(listCategorySelector);
        categorySelector.setValue(listCategorySelector.get(0));
        //Initialize price selector (RadioButton)
        priceRB.setSelected(true);
    }
}
