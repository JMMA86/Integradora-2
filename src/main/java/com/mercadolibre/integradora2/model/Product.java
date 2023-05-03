package com.mercadolibre.integradora2.model;

public class Product {
    private String name;
    private String description;
    private double price;
    private int amount;
    private ProductCategory category;
    private int timesBought;

    public Product(String name, String description, double price, int amount, int category, int timesBought) throws IndexOutOfBoundsException {
        this.name = name;
        this.description = description;
        if(price < 0 || amount < 0 || timesBought < 0) {
            throw new IllegalArgumentException("Values for price, amount and times bought must be non-negative");
        } else {
            this.timesBought = timesBought;
            this.price = price;
            this.amount = amount;
        }
        if(category < 1 || category > ProductCategory.values().length) {
            throw new IndexOutOfBoundsException("The category must be in the specified range");
        } else {
            this.category = ProductCategory.values()[category-1];
        }
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
}
