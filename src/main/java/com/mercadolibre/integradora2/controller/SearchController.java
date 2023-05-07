package com.mercadolibre.integradora2.controller;

import com.mercadolibre.integradora2.MainApplication;
import com.mercadolibre.integradora2.model.Order;
import com.mercadolibre.integradora2.model.Product;
import com.mercadolibre.integradora2.model.ProductCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

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
    private HBox orderRadioButtons;
    @FXML
    private RadioButton priceRB;
    @FXML
    private ToggleGroup searcherToggle;
    @FXML
    private RadioButton categoryRB;
    @FXML
    private RadioButton stockRB;
    @FXML
    private RadioButton namePrefixRB;
    @FXML
    private RadioButton timesRB;
    @FXML
    private RadioButton nameSuffixRB;
    //RadioButtons for orderSearch
    @FXML
    private ToggleGroup orderToggle;
    @FXML
    private HBox productRadioButtons;
    @FXML
    private RadioButton nameOrderPrefixRB;
    @FXML
    private RadioButton nameOrderSuffixRB;
    @FXML
    private RadioButton priceOrderRB;
    @FXML
    private RadioButton dateRB;
    //Category selector
    @FXML
    private ComboBox<ProductCategory> categorySelector;
    //TableView for Products
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameProduct;
    @FXML
    private TableColumn<Product, Integer> stockProduct;
    @FXML
    private TableColumn<Product, ProductCategory> categoryProduct;
    @FXML
    private TableColumn<Product, Double> priceProduct;
    @FXML
    private TableColumn<Product, Integer> boughtProduct;
    //TableView for Orders
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, String> costumerOrder;
    @FXML
    private TableColumn<Order, Double> priceOrder;
    @FXML
    private TableColumn<Order, String> dateOrder;
    //Return Button
    @FXML
    private Button returnBtn;
    @FXML
    public void selectSearcher() {
        //Managed = occupy space
        if (searcherSelector.getValue().equals("Product Searcher")) {
            categorySelector.setManaged(true);
            productRadioButtons.setVisible(true);
            productRadioButtons.setManaged(true);
            orderRadioButtons.setVisible(false);
            orderRadioButtons.setManaged(false);
            productTable.setVisible(true);
            productTable.setManaged(true);
            orderTable.setVisible(false);
            orderTable.setManaged(false);
        } else {
            categorySelector.setManaged(false);
            productRadioButtons.setVisible(false);
            productRadioButtons.setManaged(false);
            orderRadioButtons.setVisible(true);
            orderRadioButtons.setManaged(true);
            productTable.setVisible(false);
            productTable.setManaged(false);
            orderTable.setVisible(true);
            orderTable.setManaged(true);
        }
    }
    @FXML
    public void updateSearchBtn() {
        searchBtn.setDisable(lowerValueField.getText().isEmpty() || higherValueField.getText().isEmpty());
        if (categoryRB.isSelected()) {
            searchBtn.setDisable(false);
            lowerValueField.setVisible(false);
            higherValueField.setVisible(false);
            lowerValueField.setManaged(false);
            higherValueField.setManaged(false);
        } else {
            lowerValueField.setVisible(true);
            higherValueField.setVisible(true);
            lowerValueField.setManaged(true);
            higherValueField.setManaged(true);
        }
    }
    @FXML
    public void searchProduct() {
        if (higherValueField.getText().compareTo(lowerValueField.getText()) < 0) {
            MainApplication.showAlert("Error", "Invalid intervals.", Alert.AlertType.ERROR);
            return;
        }
        if (searcherSelector.getValue().equals("Product Searcher")) {
            ObservableList<Product> result;
            if (priceRB.isSelected()) {
                try {
                    result = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(MainApplication.getManager().searchProductByPrice(Double.parseDouble(lowerValueField.getText()), Double.parseDouble(higherValueField.getText())))));
                    if (orderSelector.getValue().equals("Descending Order")) {
                        Collections.reverse(result);
                    }
                    productTable.setItems(result);
                } catch (NumberFormatException e) {
                    MainApplication.showAlert("Error", "Invalid intervals.", Alert.AlertType.ERROR);
                } catch (NoSuchElementException e) {
                    MainApplication.showAlert("Error", "No products found.", Alert.AlertType.ERROR);
                }
                return;
            }
            if (stockRB.isSelected()) {
                try {
                    result = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(MainApplication.getManager().searchProductByAmount(Integer.parseInt(lowerValueField.getText()), Integer.parseInt(higherValueField.getText())))));
                    if (orderSelector.getValue().equals("Descending Order")) {
                        Collections.reverse(result);
                    }
                    productTable.setItems(result);
                } catch (NumberFormatException e) {
                    MainApplication.showAlert("Error", "Invalid intervals.", Alert.AlertType.ERROR);
                } catch (NoSuchElementException e) {
                    MainApplication.showAlert("Error", "No products found.", Alert.AlertType.ERROR);
                }
                return;
            }
            if (timesRB.isSelected()) {
                try {
                    result = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(MainApplication.getManager().searchProductsByTimesBought(Integer.parseInt(lowerValueField.getText()), Integer.parseInt(higherValueField.getText())))));
                    if (orderSelector.getValue().equals("Descending Order")) {
                        Collections.reverse(result);
                    }
                    productTable.setItems(result);
                } catch (NumberFormatException e) {
                    MainApplication.showAlert("Error", "Invalid intervals.", Alert.AlertType.ERROR);
                } catch (NoSuchElementException e) {
                    MainApplication.showAlert("Error", "No products found.", Alert.AlertType.ERROR);
                }
                return;
            }
            if (categoryRB.isSelected()) {
                try {
                    result = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(MainApplication.getManager().searchProductsByCategory(categorySelector.getValue(), categorySelector.getValue()))));
                    if (orderSelector.getValue().equals("Descending Order")) {
                        Collections.reverse(result);
                    }
                    productTable.setItems(result);
                } catch (NumberFormatException e) {
                    MainApplication.showAlert("Error", "Invalid intervals.", Alert.AlertType.ERROR);
                } catch (NoSuchElementException e) {
                    MainApplication.showAlert("Error", "No products found.", Alert.AlertType.ERROR);
                }
                return;
            }
            if (namePrefixRB.isSelected()) {
                try {
                    result = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(MainApplication.getManager().searchProductsByStrings(lowerValueField.getText(), higherValueField.getText(), true))));
                    if (orderSelector.getValue().equals("Descending Order")) {
                        Collections.reverse(result);
                    }
                    productTable.setItems(result);
                } catch (NoSuchElementException e) {
                    MainApplication.showAlert("Error", "No products found.", Alert.AlertType.ERROR);
                }
            }
            if (namePrefixRB.isSelected()) {
                try {
                    result = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(MainApplication.getManager().searchProductsByStrings(lowerValueField.getText(), higherValueField.getText(), false))));
                    if (orderSelector.getValue().equals("Descending Order")) {
                        Collections.reverse(result);
                    }
                    productTable.setItems(result);
                } catch (NoSuchElementException e) {
                    MainApplication.showAlert("Error", "No products found.", Alert.AlertType.ERROR);
                }
            }
        } else {

        }
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
        //Initialize name selector (RadioButton for orderSearcher selection)
        nameOrderPrefixRB.setSelected(true);
    }
}
