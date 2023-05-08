package com.mercadolibre.integradora2.model;

import java.util.Locale;

public class Product {
    private String name;
    private String description;
    private double price;
    private int amount;
    private ProductCategory category;
    private int timesBought;

    public Product(String name, String description, double price, int amount, int category, int timesBought) {
        this.name = name;
        this.description = description;
        if(price < 0 || amount < 0 || timesBought < 0) {
            throw new IllegalArgumentException("Values for price, amount and times bought must be non-negative");
        } else {
            this.timesBought = timesBought;
            this.price = price;
            this.amount = amount;
            this.category = ProductCategory.values()[category-1];
        }
    }

    public Product(String name, String description, double price, int amount, ProductCategory category, int timesBought) {
        this.name = name;
        this.description = description;
        this.timesBought = timesBought;
        this.price = price;
        this.amount = amount;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public int getTimesBought() {
        return timesBought;
    }

    public void setTimesBought(int timesBought) {
        this.timesBought = timesBought;
    }

    public String toString() {
        StringBuilder category = new StringBuilder();
        String[] words = this.category.name().toLowerCase().split("_");
        for (String word : words) {
            category.append(word.substring(0, 1).toUpperCase() + word.substring(1) + " ");
        }
        return name + "\n- Price: " + price + "\n- Amount: " + amount + "\n- Category: " + category + "\n- Description: " + description;
    }
}
