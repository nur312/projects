package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void shift() {
        Point point = new Point();
        point.Shift(1.0, 1.0, "test");
        assertEquals(point.getCoordinates(), "(1,00; 1,00)");
    }

    @Test
    void getCoordinates() {
        Point point = new Point();
        assertEquals(point.getCoordinates(), "(0,00; 0,00)");
    }
}