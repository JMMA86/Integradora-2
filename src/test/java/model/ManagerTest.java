package model;

import com.mercadolibre.integradora2.exception.DuplicatedElementException;
import com.mercadolibre.integradora2.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    Manager manager;
    Random rnd;

    @BeforeEach
    void initialSetup() {
        manager = new Manager();
        rnd = new Random();
    }

    void setupScenario1() {
        try {
            manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10);
        } catch (DuplicatedElementException e) {
            throw new RuntimeException(e);
        }
    }

    void setUpScenario2() {
        try {
            manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10);
            manager.addProduct("PonyMalta", "For the sugar enjoyers", 2.0, 100, 4, 15);
            manager.addProduct("The integrative task", "To suffer", 10.0, 2, 6, 15);
        } catch (DuplicatedElementException e) {
            throw new RuntimeException(e);
        }
    }

    void setUpScenario3() {
        try {
            manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10);
            manager.addProduct("Pepsi", "Enjoy the sugar", 1.5, 50, 4, 10);
        } catch (DuplicatedElementException e) {
            throw new RuntimeException(e);
        }
    }

    void setUpScenario4() {
        try {
            manager.addProduct("Minecraft", "For the miners", 150.5, 100, 8, 150);
            manager.addProduct("Lipstick", "Beauty", 150.5, 7, 7, 145);
        } catch (DuplicatedElementException e) {
            throw new RuntimeException(e);
        }
    }

    void setUpMultipleProductsAndOrders() {
        setUpScenario4();

        List<Product> aux = manager.getProducts().subList(0, 0);
        manager.addOrder("Oscar", new ArrayList<>(aux));
        aux = manager.getProducts().subList(0, 1);
        manager.addOrder("Oscar", new ArrayList<>(aux));
    }

    void setUpMultipleOrdersNonRepeatedNames() {
        setUpScenario2();

        List<Product> aux = manager.getProducts().subList(0, 3);
        manager.addOrder("Mario", new ArrayList<>(aux));
        aux = manager.getProducts().subList(1, 2);
        manager.addOrder("Martinez", new ArrayList<>(aux));
        aux = manager.getProducts().subList(0, 1);
        manager.addOrder("Felipe", new ArrayList<>(aux));
    }

    // Add product

    @Test
    void addProductValidData() {
        int limit = 10;
        for (int i = 0; i < limit; i++) {
            try {
                manager.addProduct("SampleProduct" + i, "Enjoy the sugar", rnd.nextDouble(20), rnd.nextInt(10), rnd.nextInt(1, ProductCategory.values().length - 1), rnd.nextInt(10));
            } catch (DuplicatedElementException e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals(manager.getProducts().size(), limit);
    }

    @Test
    void addProductRepeatedData() {
        setupScenario1();
        assertThrows(DuplicatedElementException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10));
    }

    @Test
    void addProductInvalidData() {
        assertThrows(IndexOutOfBoundsException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, ProductCategory.values().length + 1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", -10, 50, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, -10, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, -10, 10, -10));
    }

    // Add order

    @Test
    void addOrderSingleProduct() {
        setupScenario1();
        manager.addOrder("Yuluka", manager.getProducts());
        assertEquals(manager.getOrders().size(), 1);
    }

    @Test
    void addOrderMultipleProducts() {
        setUpScenario2();
        manager.addOrder("Martin", manager.getProducts());
        assertEquals(manager.getOrders().size(), 1);
        assertEquals(manager.getOrders().get(0).getProducts().size(), manager.getProducts().size());
    }

    //Search
    @Test
    void searchSingleProductByName() {
        setupScenario1();

        assertEquals(manager.getProducts().get(0), manager.searchProduct("CocaCola"));
    }

    @Test
    void searchProductsByRange() {
        setUpScenario2();
        setUpScenario4();

        Product[] result = manager.searchProductsByStrings("Co", "Po", false);

        for (int i = 0; i < 4; i++) {
            assertEquals(manager.getProducts().get(i), result[i]);
        }
    }

    @Test
    void searchProductsByCategory() {
        setUpScenario2();
        setUpScenario4();

        Product[] result = manager.searchProductsByCategory(ProductCategory.FOOD_AND_DRINKS, ProductCategory.STATIONERY);

        for (int i = 0; i < 3; i++) {
            assertEquals(manager.getProducts().get(i), result[i]);
        }
    }

    // Search Order
    @Test
    void searchOrdersByCustomersRepeatedNames() {
        setUpMultipleOrdersNonRepeatedNames();
        setUpMultipleProductsAndOrders();

        String[] names = {"Oscar", "Oscar"};
        Order[] orders = manager.searchOrdersByCustomersNames("Os", "Osc", false);

        for (int i = 0; i < orders.length; i++) {
            assertEquals(names[i], orders[i].getCustomerName());
        }
    }

    @Test
    void searchOrdersByTotalPriceNotFound() {
        setUpMultipleOrdersNonRepeatedNames();
        setUpMultipleProductsAndOrders();

        try {
            Order[] orders = manager.searchOrdersByTotalPrice(200, 500);
        } catch (NoSuchElementException e) {
            assertNotNull(e);
        }
    }

    @Test
    void searchOrdersByActualDate() {
        setUpMultipleOrdersNonRepeatedNames();
        setUpMultipleProductsAndOrders();

        try {
            Order[] orders = manager.searchOrdersByDate(LocalDate.now().toString(), LocalDate.now().toString());

            for (int i = 0; i < manager.getOrders().size(); i++) {
                assertEquals(manager.getOrders().get(i), orders[i]);
            }
            assertEquals(5, orders.length);
        } catch (NoSuchElementException e) {
            fail();
        }
    }
}
