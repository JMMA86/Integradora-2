package model;

import com.mercadolibre.integradora2.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

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
        manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10);
    }

    // ADD PRODUCT METHOD

    @Test
    void addProductValidData() {
        int limit = 10;
        for(int i=0; i<limit; i++) {
            manager.addProduct("SampleProduct"+i, "Enjoy the sugar", rnd.nextDouble(20), rnd.nextInt(10), rnd.nextInt(1,ProductCategory.values().length-1), rnd.nextInt(10));
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
        assertThrows(IndexOutOfBoundsException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, ProductCategory.values().length+1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", -10, 50, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, -10, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> manager.addProduct("CocaCola", "Enjoy the sugar", 1.5, -10, 10, -10));
    }

}
