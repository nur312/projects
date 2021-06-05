package com.nurzhigit;

public class Main {

    /**
     * @param args height, width and start account
     */
    public static void main(String[] args) {
        var game = CreateGame(args);
        game.Launch();
    }

    /**
     * Create instance of game
     *
     * @param args height, width and start account
     * @return Game instance
     */
    private static Game CreateGame(String[] args) {
        try {
            int width = Helper.ParseAndCheck(args[1], Helper.minWidthAndHeight, Helper.maxWidthAndHeight);
            int height = Helper.ParseAndCheck(args[0], Helper.minWidthAndHeight, Helper.maxWidthAndHeight);
            int startBalance = Helper.ParseAndCheck(args[2], Helper.minBalance, Helper.maxBalance);
            return new Game(height, width, startBalance);
        } catch (Exception ex) {
            System.out.println("Please enter legal arguments...");
            System.exit(0);
            return null;
        }
    }
}
