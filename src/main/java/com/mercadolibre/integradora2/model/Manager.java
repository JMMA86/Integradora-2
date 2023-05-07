package com.mercadolibre.integradora2.model;

import com.google.gson.Gson;
import com.mercadolibre.integradora2.exception.DuplicatedElementException;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

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
        String msj = "Files loaded successfully.";
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

    public void addOrder(String customerName, ArrayList<Product> products) {
        Order newOrder = new Order(customerName, products);
        orders.add(newOrder);
    }

    public void addProduct(String name, String description, double price, int amount, int category, int timesBought) throws IndexOutOfBoundsException, DuplicatedElementException {
        Product newProduct = new Product(name, description, price, amount, category, timesBought);
        try {
            searchProduct(name);
            throw new DuplicatedElementException("The product is already in the list");
        } catch (NoSuchElementException e) {
            products.add(newProduct);
        }
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
        return integerSearchControl(
                lower,
                upper,
                Comparator.comparingInt(Product::getAmount),
                products.stream().mapToInt(Product::getAmount)
        );
    }

    /**
     * This method manages the behavior of the searcher when a search of type integer
     * is performed
     *
     * @param lower             The lower limit of the search
     * @param upper             The upper limit of the search
     * @param productComparator A Comparator in which is going to be performed the search
     * @param intStream         An array stream containing the elements to search on.
     * @return A list of type Product
     */
    private Product[] integerSearchControl(int lower, int upper, Comparator<Product> productComparator, IntStream intStream) {
        Searcher<Integer, Product> bs = new Searcher<>();
        products.sort(productComparator);
        Integer[] elements = intStream.boxed().toArray(Integer[]::new);
        return bs.searchByRange(products, elements, lower, upper).toArray(Product[]::new);
    }

    /**
     * Search elements by strings in a generic list. Will return all the coincidences
     * that matches those elements by their prefixes or suffixes.
     *
     * @param <E>          the type of the element's array where the search will be performed.
     * @param elements     the array with the element's to search
     * @param lower        the lower limit
     * @param upper        the upper limit
     * @param suffix       the suffix if the search will be performed by prefixes or suffixes
     * @param keyExtractor the function where the keys are going to be extracted
     * @return An array with elements of type E
     * @throws NoSuchElementException When theres no T that matches with the ranges provided.
     */
    public <E> E[] searchElementsByStrings(List<E> elements, String lower, String upper, boolean suffix, Function<E, String> keyExtractor) throws NoSuchElementException {
        //Creates a new searcher that searches by strings on elements of type E
        Searcher<String, E> searcher = new Searcher<>();
        //The keys that are going to be subtracted from the original array
        String[] stringKeys;

        //Setting both limits to the same length
        String[] limits = setStringLengths(lower, upper);
        lower = limits[0];
        upper = limits[1];

        //Checking if the search is by prefix or suffix
        if (suffix) {
            //Reversing the order because the elements will be ordered in reverse by suffix
            lower = new StringBuilder(lower).reverse().toString();
            upper = new StringBuilder(upper).reverse().toString();
            //Sorting the elements by comparing the key
            //KeyExtractor is part of the functional programming implemented in java
            //Function is a functional interface that accepts a value and returns an object
            //Apply, as its name indicates, applies the given function to the keyExtractor
            elements.sort(Comparator.comparing(
                    (E e) -> new StringBuilder(keyExtractor.apply(e)).reverse().toString())
            );
            //Takes the keys from the method given by the keyExtractor and reverse them
            //before adding them to a new array of String
            stringKeys = elements.stream()
                    .map(keyExtractor)
                    .map(str -> new StringBuilder(str).reverse().toString())
                    .toArray(String[]::new);
        } else {
            //Does the same logic as with suffix, but this one extracts
            //the key directly to the stringKeys array
            elements.sort(Comparator.comparing(keyExtractor));
            stringKeys = elements.stream()
                    .map(keyExtractor)
                    .toArray(String[]::new);
        }

        //Performs the search
        //(E[]) Array.newInstance(elements.get(0).getClass(), 0) is the creating of a new
        //array based on the type E, as the program does not kno    w which will be the length of
        //the final array, it initially establishes it as a length 0, then increase its length as
        //the elements start coming
        return searcher.searchByRange((ArrayList<E>) elements, stringKeys, lower, upper).toArray((E[]) Array.newInstance(elements.get(0).getClass(), 0));
    }

    /**
     * This method search an element in a list of E elements. It works with
     * doubles and returns a list of E elements that are equal to the range
     * given by the upper and lower bounds.
     *
     * @param elements the list of E elements
     * @param lower the lower bound
     * @param upper the upper bound
     * @param keyExtractor The function where the key is extracted
     * @param <E>
     * @return
     * @throws NoSuchElementException
     */
    public <E> E[] searchElementsByDoubles(List<E> elements, Double lower, Double upper, Function<E, Double> keyExtractor) throws NoSuchElementException {
        Searcher<Double, E> searcher = new Searcher<>();
        Double[] doubleKeys;

        elements.sort(Comparator.comparing(keyExtractor));
        doubleKeys = elements.stream()
                .map(keyExtractor)
                .toArray(Double[]::new);

        return searcher.searchByRange((ArrayList<E>) elements, doubleKeys, lower, upper).toArray((E[]) Array.newInstance(elements.get(0).getClass(), 0));
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
        return searchElementsByDoubles(products, lower, upper, Product::getPrice);
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
        return integerSearchControl(
                productCategory1.ordinal(),
                productCategory2.ordinal(),
                Comparator.comparing(Product::getCategory),
                products.stream()
                        .map(Product::getCategory)
                        .mapToInt(ProductCategory::ordinal)
        );
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
        return integerSearchControl(
                lower,
                upper,
                Comparator.comparingInt(Product::getTimesBought),
                products.stream()
                        .mapToInt(Product::getTimesBought)
        );
    }

    /**
     * This function search elements between a range given by strings.
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
        return searchElementsByStrings(products, lower, upper, suffix, Product::getName);
    }

    /**
     * This function search by a customer name, return all the order that matches those coincidences.
     * If the suffix is true, it will return a list with the orders by suffix
     * <p>
     * The lower string must be alphabetically smaller than the upper string
     *
     * @param lower  The lower string
     * @param upper  The upper string
     * @param suffix If the search is made by suffixes
     * @return An array with the orders that matches those customers names
     * @throws NoSuchElementException If theres no orders with those customers names.
     */
    public Order[] searchOrdersByCustomersNames(String lower, String upper, boolean suffix) throws NoSuchElementException {
        return searchElementsByStrings(orders, lower, upper, suffix, Order::getCustomerName);
    }

    /**
     * Search by orders by their total price.
     * <p>
     * The lower limit must be smaller than the upper limit.
     *
     * @param lower the lower limit
     * @param upper the upper limit
     * @return An array with the orders that matches those prices.
     * @throws NoSuchElementException When there is no elements that matches those
     *                                characteristics
     */
    public Order[] searchOrdersByTotalPrice(double lower, double upper) throws NoSuchElementException {
        return searchElementsByDoubles(orders, lower, upper, Order::getTotalPrice);
    }

    public Order[] searchOrdersByDate() {
        return null;
    }

    /**
     * This function selects the bigger string length and set it to the
     * other string, this function does not add any character, it works with the
     * StringBuilder java class and only adds null sections equals to \u0000
     * till both lengths are equals.
     *
     * @param string1 First string
     * @param string2 Second string
     * @return An array of strings with the two strings given with the same length
     */
    private String[] setStringLengths(String string1, String string2) {
        StringBuilder temp;

        if (string1.length() > string2.length()) {
            temp = new StringBuilder(string2);
            temp.setLength(string1.length());
            string2 = temp.toString();
        } else {
            temp = new StringBuilder(string1);
            temp.setLength(string2.length());
            string1 = temp.toString();
        }

        return new String[]{string1, string2};
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
