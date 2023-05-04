package com.mercadolibre.integradora2.model;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Manager {
    private ArrayList<Product> products;
    private ArrayList<Order> orders;

    public Manager() {
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    /**
     * This function loads data from JSON files in "data" folder. In case of non-existing folder,
     * the method will create it.
     * Products and orders will be loaded to the system.
     */
    public String readData() {
        String msj = "Both files loaded successfully.";
        Gson gson = new Gson();

        File projectDir = new File(System.getProperty("user.dir"));
        File dataDirectory = new File(projectDir + "/data");
        File products = new File(projectDir + "/data/products.json");
        File orders = new File(projectDir + "/data/orders.json");

        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        }

        try {
            FileInputStream prod = new FileInputStream(products);
            FileInputStream ords = new FileInputStream(orders);
            BufferedReader prodReader = new BufferedReader(new InputStreamReader(prod));
            BufferedReader ordsReader = new BufferedReader(new InputStreamReader(ords));
            String line;
            String json = "";
            while ((line = prodReader.readLine()) != null) {
                json += line;
            }
            Product[] jsonProducts = gson.fromJson(json, Product[].class);
            this.products.addAll(Arrays.asList(jsonProducts));
            json = "";
            while ((line = ordsReader.readLine()) != null) {
                json += line;
            }
            Order[] jsonOrders = gson.fromJson(json, Order[].class);
            this.orders.addAll(Arrays.asList(jsonOrders));
        } catch (FileNotFoundException e) {
            msj = "Data not found. System will save changes from this session.";
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return msj;
    }

    /**
     * This method saves products and orders data to JSON files in the project "data" folder.
     * It will allow persistence in the system use.
     */
    public void writeData() {
        File projectDir = new File(System.getProperty("user.dir"));
        File products = new File(projectDir + "/data/products.json");
        File orders = new File(projectDir + "/data/orders.json");

        Gson gson = new Gson();
        String jsonProducts = gson.toJson(this.products);
        String jsonOrders = gson.toJson(this.orders);
        try {
            FileOutputStream fosProducts = new FileOutputStream(products);
            FileOutputStream fosOrders = new FileOutputStream(orders);
            fosProducts.write(jsonProducts.getBytes(StandardCharsets.UTF_8));
            fosOrders.write(jsonOrders.getBytes(StandardCharsets.UTF_8));
            fosProducts.close();
            fosOrders.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void addProduct(String name, String description, double price, int amount, int category, int timesBought) throws IndexOutOfBoundsException {
        Product newProduct = new Product(name, description, price, amount, category, timesBought);

        // TODO: look for repeated product

        products.add(newProduct);
    }


    /**
     * This function searches a product by its name along the products array
     *
     * @param name The product's name to be searched
     * @return The product searched by the user
     */
    public Product searchProduct(String name) throws NoSuchElementException {
        Searcher<String> bs = new Searcher<>();
        products.sort(Comparator.comparing(Product::getName));
        String[] names = products.stream().map(Product::getName).toArray(String[]::new);
        int pos = bs.binarySearch(names, name, 0, products.size() - 1, false);
        return products.get(pos);
    }

    /**
     * This function search a product by its amount in
     * a range given by the user.
     * <p>
     * The lower limit cannot be greater than the upper limit.
     *
     * @param lower The lower limit
     * @param upper The upper limit
     * @return A list with the products that matches those elements.
     */
    public Product[] searchProductByAmount(int lower, int upper) throws NoSuchElementException {
        Searcher<Integer> bs = new Searcher<>();
        products.sort(Comparator.comparingInt(Product::getAmount));
        Integer[] amounts = products.stream().mapToInt(Product::getAmount).boxed().toArray(Integer[]::new);
        int[] indexes = bs.searchByRange(amounts, lower, upper);
        ArrayList<Product> result = new ArrayList<>();
        for (int i = indexes[0]; i <= indexes[1]; i++) {
            result.add(products.get(i));
        }
        return result.toArray(Product[]::new);
    }

    /**
     * This function search a product by its price in a range given
     * by the user.
     * <p>
     * The lower limit cannot be grater thant the upper limit.
     *
     * @param lower
     * @param upper
     * @return
     */
    public Product[] searchProductByPrice(double lower, double upper) throws NoSuchElementException {
        Searcher<Double> bs = new Searcher<>();
        products.sort(Comparator.comparingDouble(Product::getPrice));
        Double[] prices = products.stream().mapToDouble(Product::getPrice).boxed().toArray(Double[]::new);
        int[] indexes = bs.searchByRange(prices, lower, upper);
        ArrayList<Product> result = new ArrayList<>();
        for (int i = indexes[0]; i <= indexes[1]; i++) {
            result.add(products.get(i));
        }
        return result.toArray(Product[]::new);
    }

    public Product[] searchProductsByCategory(ProductCategory productCategory1, ProductCategory productCategory2) throws NoSuchElementException {
        Searcher<Integer> bs = new Searcher<>();
        products.sort(Comparator.comparing(Product::getCategory));
        Integer[] categories = products.stream().map(Product::getCategory).map(ProductCategory::ordinal).toArray(Integer[]::new);
        int[] indexes = bs.searchByRange(categories, productCategory1.ordinal(), productCategory2.ordinal());
        ArrayList<Product> result = new ArrayList<>();
        for (int i = indexes[0]; i <= indexes[1]; i++) {
            result.add(products.get(i));
        }
        return result.toArray(Product[]::new);
    }

    public Product[] searchProductsByTimesBought(int lower, int upper) throws NoSuchElementException {
        Searcher<Integer> bs = new Searcher<>();
        products.sort(Comparator.comparingInt(Product::getTimesBought));
        Integer[] times = products.stream().mapToInt(Product::getTimesBought).boxed().toArray(Integer[]::new);
        int[] indexes = bs.searchByRange(times, lower, upper);
        ArrayList<Product> result = new ArrayList<>();
        for (int i = indexes[0]; i <= indexes[1]; i++) {
            result.add(products.get(i));
        }
        return result.toArray(Product[]::new);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

}
