package com.mercadolibre.integradora2.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.gson.Gson;

public class Manager {
    private ArrayList<Product> products;

    public Manager() {
        this.products = new ArrayList<>();
    }

    public static void readData() {
        Gson gson = new Gson();
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
