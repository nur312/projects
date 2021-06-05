package sample;

import static java.lang.Thread.sleep;

public class Main {
    /**
     * Converts degrees to radians
     * @param angle degrees
     * @return radians
     */
    public static double ToRadian(double angle) {
        return (angle*Math.PI)/180;
    }
    public static Point GetPoint(String[] args) {
        if(args.length == 0) {
            return new Point();
        }
        if(args.length != 2) {
            return null;
        }
        try{
            double x, y;

            x = Double.parseDouble(args[0]);
            y = Double.parseDouble(args[1]);
            return new Point(x, y);
        }catch (Exception ex) {
            return null;
        }
    }

    /**
     * arguments: empty or two double x and y
     * @param args arguments
     */
    public static void main(String[] args) {

        Point point = GetPoint(args);
        if (point == null) {
            System.out.println("Invalid arguments...");
            return;
        }

        Animal[] animals = CreateAnimals(point);

        // Launch animals
        for(var a : animals) {
            a.start();
        }

        ShowEveryTwoSeconds(point);

        SleepOneSecondAndInterruptAnimals(animals);

        System.out.println("> Finish " + point.getCoordinates());
    }

    /**
     * Creates animals according to the condition
     * @param point point
     * @return animals
     */
    private static Animal[] CreateAnimals(Point point) {
        return new Animal[]{
                new Animal(ToRadian(60), 1 + 9 * Animal.random.nextDouble(), point, "Swan"),
                new Animal(ToRadian(180), 1 + 9 * Animal.random.nextDouble(), point, "Crayfish"),
                new Animal(ToRadian(300), 1 + 9 * Animal.random.nextDouble(), point, "Pike")
        };
    }

    /**
     * Shows point's coordinates every two seconds.
     * @param point point
     */
    private static void ShowEveryTwoSeconds(Point point) {
        for (int i = 0; i < 12; ++i) {
            try {
                sleep(2000);
                System.out.println("> " + point.getCoordinates());
            } catch (InterruptedException ex) {
                System.out.println("Error: main thread interrupted");
            }

        }
    }

    /**
     * Sleeps one second and interrupt animals
     * @param animals animals
     */
    private static void SleepOneSecondAndInterruptAnimals(Animal[] animals) {
        try {
            sleep(1000);
            for(var a : animals) {
                a.interrupt();
            }
        } catch (InterruptedException ex) {
            System.out.println("Error: main thread interrupted");
        }
    }
}

