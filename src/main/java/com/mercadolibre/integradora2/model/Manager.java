package com.mercadolibre.integradora2.model;

import java.util.ArrayList;
import java.util.Comparator;

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


    /**
     * THis function searches a product by its name along the products array
     *
     * @param name The product's name to be searched
     * @return The product searched by the user
     */
    public Product searchProduct(String name) {
        Searcher<String> bs = new Searcher<>();
        String[] names = products.stream().map(Product::getName).sorted().toArray(String[]::new);
        int pos = bs.binarySearch(names, name,0, products.size() - 1, false);
        return products.get(pos);
    }

    public Product searchProduct(int amount) {
        Searcher<Integer> bs = new Searcher<>();
        Integer[] amounts = products.stream().mapToInt(Product::getAmount).sorted().toArray(Integer[]::new);
        int pos = bs.binarySearch(amounts, amount, 0, products.size() - 1, false);
        return products.get(pos);
    }

    public Product searchProduct(double price) {
        Searcher<Double> bs = new Searcher<>();
        Double[] prices = products.stream().mapToDouble(Product::getPrice).sorted().toArray(Double[]::new);
        int pos = bs.binarySearch(prices, price, 0, products.size() - 1, false);
        return products.get(pos);
    }

    public Product[] searchProductsBycategory(int categoryValue) {
        
        return null;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

}
