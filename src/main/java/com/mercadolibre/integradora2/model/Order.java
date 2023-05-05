package com.mercadolibre.integradora2.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private String customerName;
    private final ArrayList<Product> products;
    private final LocalDate DATE;
    private double totalPrice = 0;

    public Order(String customerName, ArrayList<Product> products) {
        this.customerName = customerName;
        this.products = products;
        this.DATE = LocalDate.now();
        for(Product p : products) {
            totalPrice += p.getPrice();
        }
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

    public LocalDate getDATE() {
        return DATE;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}
