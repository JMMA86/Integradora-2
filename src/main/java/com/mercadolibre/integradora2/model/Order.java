package com.mercadolibre.integradora2.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private String customerName;
    private final ArrayList<Product> products;
    private final LocalDate DATE;
    private double totalPrice;

    public Order(String customerName) {
        this.customerName = customerName;
        this.products = new ArrayList<>();
        this.DATE = LocalDate.now();
        this.totalPrice = 0;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
        totalPrice += product.getPrice();
    }

    public LocalDate getDATE() {
        return DATE;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}
