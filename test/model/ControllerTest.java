package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    Controller controller;
    Random rnd;
    @BeforeEach
    void initialSetup() {
        controller = new Controller();
        rnd = new Random();
    }

    void setupScenario1() {
        controller.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10);
    }

    // ADD PRODUCT METHOD

    @Test
    void addProductValidData() {
        int limit = 10;
        for(int i=0; i<limit; i++) {
            controller.addProduct("SampleProduct"+i, "Enjoy the sugar", rnd.nextDouble(20), rnd.nextInt(10), rnd.nextInt(1,ProductCategory.values().length-1), rnd.nextInt(10));
        }
        assertEquals(controller.getProducts().size(), limit);
    }

    @Test
    void addProductRepeatedData() {
        setupScenario1();
        controller.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 4, 10);
        assertEquals(controller.getProducts().size(), 1);
    }

    @Test
    void addProductInvalidData() {
        assertThrows(IndexOutOfBoundsException.class, () -> controller.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, ProductCategory.values().length+1, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> controller.addProduct("CocaCola", "Enjoy the sugar", 1.5, 50, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> controller.addProduct("CocaCola", "Enjoy the sugar", -10, 50, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> controller.addProduct("CocaCola", "Enjoy the sugar", 1.5, -10, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> controller.addProduct("CocaCola", "Enjoy the sugar", 1.5, -10, 10, -10));
    }

}
