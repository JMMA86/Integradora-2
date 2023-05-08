package model;

import com.mercadolibre.integradora2.exception.DuplicatedElementException;
import com.mercadolibre.integradora2.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    Manager manager;
    Random rnd;

    Product sampleProduct;

    @BeforeEach
    void initialSetup() {
        manager = new Manager();
        rnd = new Random();
    }
    String getFileContent(String file) throws IOException {
        return Files.readString(Path.of(System.getProperty("user.dir") + "/data/" + file));
    }

    private void setupEmptyInitialData() {
        try {
            Files.delete(Path.of(System.getProperty("user.dir") + "/data/orders.json"));
            Files.delete(Path.of(System.getProperty("user.dir") + "/data/products.json"));
        } catch(IOException e) {
            e.getStackTrace();
        }
        manager.writeData();
    }

    // SETUPS

    void setupInitializeMultipleProducts() {
        try {
            manager.addProduct("Adidas Ultraboost 21", "Responsive and cushioned running shoes", 179.99, 20, 6, 7);
            manager.addProduct("Cadbury Dairy Milk Chocolate", "Creamy milk chocolate bar", 2.99, 250, 4, 3);
            manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10);
            manager.addProduct("Harry Potter and the Philosopher's Stone", "The first book in the Harry Potter series", 12.99, 100, 1, 50);
            manager.addProduct("iPhone 12 Pro", "Apple's latest flagship phone", 999.99, 25, 2, 15);
            manager.addProduct("Levi's 501 Jeans", "Classic straight leg jeans", 69.99, 50, 3, 20);
            manager.addProduct("Lindt Swiss Chocolate", "Smooth and creamy milk chocolate", 3.99, 200, 4, 5);
            manager.addProduct("Lipstick", "Beauty", 150.5, 7, 7, 145);
            manager.addProduct("Minecraft", "For the miners", 150.5, 100, 8, 150);
            manager.addProduct("Monopoly Board Game", "Classic board game of buying and selling properties", 29.99, 40, 8, 18);
            manager.addProduct("Nike Air Max 270", "Stylish and comfortable sneakers", 129.99, 30, 6, 12);
            manager.addProduct("Nivea Soft Moisturizing Cream", "Quick-absorbing cream for soft skin", 6.99, 150, 7, 8);
            manager.addProduct("Pepsi", "Enjoy the sugar", 1.5, 50, 4, 10);
            manager.addProduct("PonyMalta", "For the sugar enjoyers", 2.0, 100, 4, 15);
            manager.addProduct("Staedtler Triplus Fineliner Pens", "Fine tip colored pens for precise writing", 14.99, 75, 5, 30);
            manager.addProduct("The integrative task", "To suffer", 10.0, 2, 6, 15);
        } catch(DuplicatedElementException e) {
            e.printStackTrace();
        }
    }

    void setupInitializeMultipleOrders() {
        setupInitializeMultipleProducts();
        List<Product> aux;
        aux = manager.getProducts().subList(0, 3);
        manager.addOrder("Mario", new ArrayList<>(aux));
        aux = manager.getProducts().subList(1, 2);
        manager.addOrder("Martinez", new ArrayList<>(aux));
        aux = manager.getProducts().subList(0, 1);
        manager.addOrder("Felipe", new ArrayList<>(aux));
        aux = manager.getProducts().subList(3, 6);
        manager.addOrder("Jessica", new ArrayList<>(aux));
        aux = manager.getProducts().subList(2, 4);
        manager.addOrder("Eric", new ArrayList<>(aux));
        aux = manager.getProducts().subList(1, 3);
        manager.addOrder("Julia", new ArrayList<>(aux));
    }

    void setupPreviouslySavedData() {
        setupInitializeMultipleProducts();
        setupInitializeMultipleOrders();
        manager.writeData();
    }

    // Add Product Testing

    @Test
    void addSingleProduct() {
        try {
            manager.addProduct("Sports Ball", "A ball...for sports", 10.5, 50, 8,40);
        } catch(DuplicatedElementException e ) {
            e.printStackTrace();
        }
        assertEquals(manager.getProducts().size(), 1);
    }

    @Test
    void addRepeatedProduct() {
        setupInitializeMultipleProducts();
        assertThrows(DuplicatedElementException.class, () -> manager.addProduct( manager.getProducts().get(0).getName(), "desc", 1, 50, 4, 10 ));
    }

    @Test
    void addProductInvalidData() {
        assertThrows(IndexOutOfBoundsException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, ProductCategory.values().length + 1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", -10, 50, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, -10, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, -10, 10, -10));
    }

    // Add Order testing

    @Test
    void addOrderSingleProduct() {
        setupInitializeMultipleProducts();
        manager.addOrder("Yuluka", new ArrayList<>(manager.getProducts().subList(0,1)));
        assertEquals(manager.getOrders().size(), 1);
        assertEquals(manager.getOrders().get(0).getProducts().size(),1);
    }

    @Test
    void addOrderMultipleProducts() {
        setupInitializeMultipleProducts();
        manager.addOrder("Yuluka", new ArrayList<>(manager.getProducts().subList(0,manager.getProducts().size()))  );
        assertEquals(manager.getOrders().size(), 1);
        assertEquals(manager.getOrders().get(0).getProducts().size(), manager.getProducts().size());
    }

    @Test
    void addOrderEmptyOrder() {
        setupInitializeMultipleProducts();
        assertThrows(Exception.class, () -> manager.addOrder("Yuluka", new ArrayList<>()));
    }

    @Test
    void addRepeatedOrder() {
        setupInitializeMultipleOrders();
        int initialAmount = manager.getOrders().size();
        manager.addOrder(manager.getOrders().get(0).getCustomerName(), new ArrayList<>(manager.getProducts().subList(0,1)));
        assertEquals(manager.getOrders().size(), initialAmount+1);
    }

    // Search Product Testing
    @Test
    void searchSingleProductByName() {
        setupInitializeMultipleProducts();
        assertEquals(manager.getProducts().get(0), manager.searchProduct(manager.getProducts().get(0).getName()));
    }

    @Test
    void searchProductsByPrice() {
        setupInitializeMultipleProducts();

        // linear verification
        double lower = 50.2, upper = 100.2;
        ArrayList<Product> foundProducts = new ArrayList<>();
        for(Product p : manager.getProducts()) {
            if(p.getPrice() >= lower && p.getPrice() <= upper ) {
                foundProducts.add(p);
            }
        }

        // binary search
        Product[] output = manager.searchProductByPrice(lower, upper);

        // print for debugging
        System.out.println("expected:");
        for(Product p : foundProducts) System.out.println(p.getPrice());
        System.out.println("output:");
        for(Product p : output) System.out.println(p.getPrice());

        // asssertion
        assertEquals(output.length, foundProducts.size());
    }

    @Test
    void searchProductByCategory() {
        setupInitializeMultipleProducts();

        // linear verification
        int lower = 2, upper = 4;
        ArrayList<Product> foundProducts = new ArrayList<>();
        for(Product p : manager.getProducts()) {
            if(p.getCategory().ordinal() >= lower-1 && p.getCategory().ordinal() <= upper-1 ) {
                foundProducts.add(p);
            }
        }
        foundProducts.sort(Comparator.comparing(Product::getCategory));

        // binary search
        Product[] output = manager.searchProductsByCategory(ProductCategory.values()[lower-1], ProductCategory.values()[upper-1]);

        // print for debugging
        System.out.println("expected:");
        for(Product p : foundProducts) System.out.println(p.getCategory().ordinal());
        System.out.println("output:");
        for(Product p : output) System.out.println(p.getCategory().ordinal());

        // assertion
        assertEquals(foundProducts.size(), output.length);
    }

    @Test
    void searchProductByStock() {
        setupInitializeMultipleProducts();

        // linear verification
        int lower = 30, upper = 200;
        ArrayList<Product> foundProducts = new ArrayList<>();
        for(Product p : manager.getProducts()) {
            if( p.getAmount() >= lower && p.getAmount() <= upper) {
                foundProducts.add(p);
            }
        }
        foundProducts.sort(Comparator.comparing(Product::getAmount));

        // binary search
        Product[] output = manager.searchProductByAmount(lower, upper);

        // print for debugging
        System.out.println("expected");
        for(Product p : foundProducts) System.out.println(p.getAmount());
        System.out.println("output:");
        for(Product p : output) System.out.println(p.getAmount());

        // assertion
        assertEquals(output.length, foundProducts.size());
    }

    @Test
    void searchProductsByTimesBought() {
        setupInitializeMultipleProducts();

        // linear verification
        int lower = 12, upper = 40;
        ArrayList<Product> foundProducts = new ArrayList<>();
        for(Product p : manager.getProducts()) {
            if( p.getTimesBought() >= lower && p.getTimesBought() <= upper) {
                foundProducts.add(p);
            }
        }
        foundProducts.sort(Comparator.comparing(Product::getTimesBought));

        // binary search
        Product[] output = manager.searchProductsByTimesBought(lower, upper);

        // print for debugging
        System.out.println("expected:");
        for(Product p : foundProducts) System.out.println(p.getTimesBought());
        System.out.println("output:");
        for(Product p : output) System.out.println(p.getTimesBought());

        // assertion
        assertEquals(foundProducts.size(), output.length);
    }

    @Test
    void searchProductByPrefix() {
        setupInitializeMultipleProducts();

        // linear verification
        String prefix = "Ni";
        ArrayList<Product> foundProducts = new ArrayList<>();
        for(Product p : manager.getProducts()) {
            if( p.getName().startsWith(prefix) ) {
                foundProducts.add(p);
            }
        }
        foundProducts.sort(Comparator.comparing(Product::getName));

        // binary search
        Product[] output = manager.searchProductsByStrings(prefix, "Nj", false);

        // print for debugging
        System.out.println("expected");
        for(Product p : foundProducts) System.out.println(p.getName());
        System.out.println("output:");
        for(Product p : output) System.out.println(p.getName());

        // assertion
        assertEquals(foundProducts.size(), output.length);
    }

    @Test
    void searchProductBySuffix() {
        setupInitializeMultipleProducts();

        // linear verification
        String suffix = "one";
        ArrayList<Product> foundProducts = new ArrayList<>();
        for(Product p : manager.getProducts()) {
            if( p.getName().endsWith(suffix) ) {
                foundProducts.add(p);
            }
        }
        foundProducts.sort(Comparator.comparing(Product::getName));

        // binary search
        Product[] output = manager.searchProductsByStrings(suffix, "pne", true);

        // print for debugging
        System.out.println("expected");
        for(Product p : foundProducts) System.out.println(p.getName());
        System.out.println("output:");
        for(Product p : output) System.out.println(p.getName());

        // assertion
        assertEquals(foundProducts.size(), output.length);
    }

    // Search Order testing


    // Export to Json Testing

    @Test
    void exportToJsonEmptyData() throws IOException {
        setupEmptyInitialData();
        String previousOrders = getFileContent("orders.json");
        String previousProducts = getFileContent("products.json");
        manager.writeData();
        String newOrders = getFileContent("orders.json");
        String newProducts = getFileContent("products.json");
        assertEquals(previousOrders, newOrders);
        assertEquals(previousProducts, newProducts);
    }

    @Test
    void exportToJsonNonEmptyData() throws IOException {
        setupEmptyInitialData();
        String previousOrders = getFileContent("orders.json");
        String previousProducts = getFileContent("products.json");

        // modifying the current data
        try {
            manager.addProduct("Sports Ball", "A ball...for sports", 10.5, 50, 8,40);
        } catch(DuplicatedElementException e ) {
            e.printStackTrace();
        }
        manager.addOrder("Yuluka", new ArrayList<>(manager.getProducts().subList(0,1)));

        manager.writeData();
        String newOrders = getFileContent("orders.json");
        String newProducts = getFileContent("products.json");
        assertNotEquals(previousOrders, newOrders);
        assertNotEquals(previousProducts, newProducts);
    }

    // Import from Json Testing

    @Test
    void importFromJsonNonEmptyData() {
        setupEmptyInitialData();
        setupPreviouslySavedData();
        assertNotEquals(manager.getProducts().size(), 0);
        assertNotEquals(manager.getOrders().size(), 0);
    }

    @Test
    void importFromJsonEmptyData() {
        setupEmptyInitialData();
        manager.readData();
        assertEquals(manager.getProducts().size(), 0);
        assertEquals(manager.getOrders().size(), 0);
    }

}
