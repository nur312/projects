package com.nurzhigit;

import java.util.Random;
import java.util.Scanner;

/**
 * Useful static constants and methods
 */
public final class Helper {
    public static final Random random = new Random();
    public static final Scanner scanner = new Scanner(System.in);
    public static final int minWidthAndHeight = 6;
    public static final int maxWidthAndHeight = 30;
    public static final int minBalance = 500;
    public static final int maxBalance = 15000;
    public static final int minShopPrice = 50;
    public static final int maxShopPrice = 500;
    // Начальная компенсация магазина 0.5*N 0.9*N, где N - ShopPrice
    public static final float minPenaltyCoefficient = 0.01f;
    public static final float maxPenaltyCoefficient = 0.1f;
    public static final float minDebtCoefficient = 1.0f;
    public static final float maxDebtCoefficient = 3.0f;
    public static final float minCreditCoefficient = 0.002f;
    public static final float maxCreditCoefficient = 0.2f;
    public static final float minImprovementCoefficient = 0.1f;
    public static final float maxImprovementCoefficient = 2f;
    public static final float minCompensationCoefficient = 0.1f;
    public static final float maxCompensationCoefficient = 1;

    /**
     * Parse and check number between min and max
     *
     * @param str number to string
     * @param min min
     * @param max max
     * @return result
     */
    public static int ParseAndCheck(String str, int min, int max) {
        int num = Integer.parseInt(str);
        if (num >= min && num <= max) {
            return num;
        }
        throw new IllegalArgumentException("Please enter legal args...");
    }

    /**
     * Get random float number between min and max
     *
     * @param min min
     * @param max max
     * @return random number
     */
    public static float GetRandomFloatBetween(float min, float max) {
        int delta = (int) (max - min) > 0 ? random.nextInt((int) (max - min)) : 0;
        float d = min + delta + (float) random.nextDouble();
        while (!(d >= min && d <= max)) {
            delta = (int) (max - min) > 0 ? random.nextInt((int) (max - min)) : 0;
            d = min + delta + (float) random.nextDouble();
        }
        return d;
    }

    /**
     * Get random integer number between min and max
     *
     * @param min min
     * @param max max
     * @return random integer number
     */
    public static int GetRandomIntBetween(int min, int max) {
        int i = min + random.nextInt(max - min + 1);
        while (!(i >= min && i <= max)) {
            i = min + random.nextInt(max - min + 1);
        }
        return i;
    }

    /**
     * Print separator
     */
    public static void PrintWaves() {
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    /**
     * Get char
     */
    public static void GetChar() {
        System.out.println("Press enter...");
        Helper.scanner.nextLine();
    }
}
