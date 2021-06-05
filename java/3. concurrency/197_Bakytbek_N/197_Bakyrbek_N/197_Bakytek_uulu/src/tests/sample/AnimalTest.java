package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void getAnimalName() {
        Animal pike = new Animal(1, 1 + 9 * Animal.random.nextDouble(), new Point(), "Pike");
        assertEquals("Pike", pike.getAnimalName());
    }

    @Test
    void run() throws InterruptedException {
        Animal pike = new Animal(1, 1 + 9 * Animal.random.nextDouble(), new Point(), "Pike");

        pike.start();

        Thread.sleep(10000);

        pike.interrupt();
    }
}