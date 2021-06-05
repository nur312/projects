package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void toRadian() {
        assertEquals(Main.ToRadian(180), Math.PI);
    }

    @Test
    void main() {
        // проверил по репорту, что про просыпаюстся и сдвигают
        // точку правильным образом.

    }
}