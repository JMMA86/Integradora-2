package com.mercadolibre.integradora2.model;

import com.google.gson.Gson;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Manager {
    private final ArrayList<Product> products;
    private final ArrayList<Order> orders;

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
        String msj = "Files loaded successfully.";
        Gson gson = new Gson();

        File projectDir = new File(System.getProperty("user.dir"));
        File dataDirectory = new File(projectDir+"/data");
        File products = new File(projectDir+"/data/products.json");
        File orders = new File(projectDir+"/data/orders.json");

        if(!dataDirectory.exists()){
            dataDirectory.mkdirs();
        }

        try {
            FileInputStream prod = new FileInputStream(products);
            FileInputStream ords = new FileInputStream(orders);
            BufferedReader prodReader = new BufferedReader(new InputStreamReader(prod));
            BufferedReader ordsReader = new BufferedReader(new InputStreamReader(ords));
            String line;
            StringBuilder json = new StringBuilder();
            while ((line = prodReader.readLine()) != null) {
                json.append(line);
            }
            Product[] jsonProducts = gson.fromJson(json.toString(), Product[].class);
            this.products.addAll(Arrays.asList(jsonProducts));
            json = new StringBuilder();
            while ((line = ordsReader.readLine()) != null) {
                json.append(line);
            }
            Order[] jsonOrders = gson.fromJson(json.toString(), Order[].class);
            this.orders.addAll(Arrays.asList(jsonOrders));
        } catch (FileNotFoundException e) {
            msj = "Data not found. System will save changes from this session.";
        } catch (IOException e){
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
        File products = new File(projectDir+"/data/products.json");
        File orders = new File(projectDir+"/data/orders.json");

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
        } catch (IOException e){
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
        Searcher<String, Product> bs = new Searcher<>();
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
        Searcher<Integer, Product> bs = new Searcher<>();
        products.sort(Comparator.comparingInt(Product::getAmount));
        Integer[] amounts = products.stream().mapToInt(Product::getAmount).boxed().toArray(Integer[]::new);
        return bs.searchByRange(products, amounts, lower, upper).toArray(Product[]::new);
    }

    /**
     * This function search a product by its price in a range given
     * by the user.
     * <p>
     * The lower limit cannot be grater thant the upper limit.
     *
     * @param lower the lower limit to search
     * @param upper the upper limit to search
     * @return A list with the products between those ranges
     */
    public Product[] searchProductByPrice(double lower, double upper) throws NoSuchElementException {
        Searcher<Double, Product> bs = new Searcher<>();
        products.sort(Comparator.comparingDouble(Product::getPrice));
        Double[] prices = products.stream().mapToDouble(Product::getPrice).boxed().toArray(Double[]::new);
        return bs.searchByRange(products, prices, lower, upper).toArray(Product[]::new);
    }

    /**
     * This method search a product by a ProductCategory, the search
     * is based on the ordinals of the category.
     * <p>
     * The productCategory1 ordinal must be smaller than the productCategory2 ordinal.
     *
     * @param productCategory1 The lower limit to search
     * @param productCategory2 The upper limit to search
     * @return An array filled with all the products with those categories
     * @throws NoSuchElementException When a category is not registered in any product.
     */
    public Product[] searchProductsByCategory(ProductCategory productCategory1, ProductCategory productCategory2) throws NoSuchElementException {
        Searcher<Integer, Product> bs = new Searcher<>();
        products.sort(Comparator.comparing(Product::getCategory));
        Integer[] categories = products.stream().map(Product::getCategory).map(ProductCategory::ordinal).toArray(Integer[]::new);
        return bs.searchByRange(products, categories, productCategory1.ordinal(), productCategory2.ordinal()).toArray(Product[]::new);
    }

    /**
     * This function search a product by the times it was bought.
     * <p>
     * The lower limit must be smaller
     *
     * @param lower The lower limit to search
     * @param upper The upper limit to search
     * @return An array of products between the limits specified.
     * @throws NoSuchElementException When the lower limit is bigger than the biggest times bought in products
     *                                or when the upper limit is smaller than the smaller times bought in products.
     */
    public Product[] searchProductsByTimesBought(int lower, int upper) throws NoSuchElementException {
        Searcher<Integer, Product> bs = new Searcher<>();
        products.sort(Comparator.comparingInt(Product::getTimesBought));
        Integer[] times = products.stream().mapToInt(Product::getTimesBought).boxed().toArray(Integer[]::new);
        return bs.searchByRange(products, times, lower, upper).toArray(Product[]::new);
    }

    /**
     * This function search elements between a range given by Strings.
     * It can search by prefixes or suffixes (Not both at the same time),
     * the more letters are given to the searcher, the most accurate will be output.
     * <p>
     * The lower limit must be smaller than the upper limit taking on count the
     * alphabetical order.
     * <p>
     * It doesn't matter the lengths of the Strings as the program will balance them
     *
     * @param lower  The lower limit detailed as a String
     * @param upper  The upper limit detailed as a String
     * @param suffix If the search will be performed with suffixes
     * @return An array with the products between that range
     */
    public Product[] searchProductsByStrings(String lower, String upper, boolean suffix) throws NoSuchElementException {
        Searcher<String, Product> bs = new Searcher<>();
        String[] names;
        StringBuilder temp;

        //Making sure that both limits have the same length
        if (lower.length() > upper.length()) {
            temp = new StringBuilder(upper);
            temp.setLength(lower.length());
            upper = temp.toString();
        } else {
            temp = new StringBuilder(lower);
            temp.setLength(upper.length());
            lower = temp.toString();
        }

        if (suffix) {
            lower = new StringBuilder(lower).reverse().toString();
            upper = new StringBuilder(upper).reverse().toString();
            products.sort(Comparator.comparing((Product p) -> new StringBuilder(p.getName()).reverse().toString()));
            names = products.stream().map(Product::getName).map(str -> new StringBuilder(str).reverse().toString()).toArray(String[]::new);
        } else {
            products.sort(Comparator.comparing(Product::getName));
            names = products.stream().map(Product::getName).toArray(String[]::new);
        }

        return bs.searchByRange(products, names, lower, upper).toArray(Product[]::new);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

}
