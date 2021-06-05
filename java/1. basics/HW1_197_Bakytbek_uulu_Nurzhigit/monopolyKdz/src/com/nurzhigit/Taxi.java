package com.nurzhigit;

/**
 * Taxi
 */
public final class Taxi extends Cell {
    /**
     * @return random distance [3,5]
     */
    public static int GetDistance() {
        return 3 + Helper.random.nextInt(3);
    }
}
