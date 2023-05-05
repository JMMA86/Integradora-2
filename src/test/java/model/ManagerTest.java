package model;

import com.mercadolibre.integradora2.model.Manager;
import com.mercadolibre.integradora2.model.Product;
import com.mercadolibre.integradora2.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ManagerTest {
    Manager manager;
    Random rnd;

    @BeforeEach
    void initialSetup() {
        manager = new Manager();
        rnd = new Random();
    }

    void setupScenario1() {
        manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10);
    }

    void setUpScenario2() {
        manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10);
        manager.addProduct("PonyMalta", "For the sugar enjoyers", 2.0, 100, 4, 15);
        manager.addProduct("The integrative task", "To suffer", 10.0, 2, 6, 15);
    }

    void setUpScenario3() {
        manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10);
        manager.addProduct("Pepsi", "Enjoy the sugar", 1.5, 50, 4, 10);
    }

    void setUpScenario4() {
        manager.addProduct("Minecraft", "For the miners", 150.5, 100, 8, 150);
        manager.addProduct("Lipstick", "Beauty", 150.5, 7, 7, 145);
    }

    // ADD PRODUCT METHOD

    @Test
    void addProductValidData() {
        int limit = 10;
        for (int i = 0; i < limit; i++) {
            manager.addProduct("SampleProduct" + i, "Enjoy the sugar", rnd.nextDouble(20), rnd.nextInt(10), rnd.nextInt(1, ProductCategory.values().length - 1), rnd.nextInt(10));
        }
        assertEquals(manager.getProducts().size(), limit);
    }

    @Test
    void addProductRepeatedData() {
        setupScenario1();
        manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10);
        assertEquals(manager.getProducts().size(), 1);
    }

    @Test
    void addProductInvalidData() {
        assertThrows(IndexOutOfBoundsException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, ProductCategory.values().length + 1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", -10, 50, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, -10, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, -10, 10, -10));
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

        for (Product mx : result) {
            System.out.println(mx.getName());
        }

        for (int i = 0; i < 4; i++) {
            assertEquals(manager.getProducts().get(i), result[i]);
        }
    }
}
