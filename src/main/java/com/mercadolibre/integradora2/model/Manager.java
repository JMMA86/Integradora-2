package com.mercadolibre.integradora2.model;

import java.util.ArrayList;

public class Manager {
    private ArrayList<Product> products;

    public Manager() {
        this.products = new ArrayList<>();
    }

    public void addProduct(String name, String description, double price, int amount, int category, int timesBought) throws IndexOutOfBoundsException {
        Product newProduct = new Product(name, description, price, amount, category, timesBought);

        // TODO: look for repeated product

        products.add(newProduct);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

}
