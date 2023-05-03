package com.mercadolibre.integradora2.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.gson.Gson;

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
            Product[] jsonProducts = gson.fromJson(String.valueOf(json), Product[].class);
            this.products.addAll(Arrays.asList(jsonProducts));
            json.setLength(0);
            while ((line = ordsReader.readLine()) != null) {
                json.append(line);
            }
            Order[] jsonOrders = gson.fromJson(String.valueOf(json), Order[].class);
            this.orders.addAll(Arrays.asList(jsonOrders));
        } catch (FileNotFoundException e) {
            msj = "Data not found. System will save changes from this session.";
        } catch (IOException e){
            //e.printStackTrace();
        }
        return msj;
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
