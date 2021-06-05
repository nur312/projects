package sample;

import java.util.Random;

public final class Animal extends Thread {

    private final double angle;
    private final double strength;
    private final Point point;
    private final String name;
    static final Random random = new Random();

    public Animal(double angle, double strength, Point point, String name) {
        this.angle = angle;
        this.strength = strength;
        this.point = point;
        this.name = name;
    }

    /**
     * Returns animal's name
     */
    public String getAnimalName() {
        return name;
    }

    /**
     * Runs pulling process
     */
    @Override
    public void run (){
        while (!isInterrupted()) {
            try{
                point.Shift(strength * Math.cos(angle), strength * Math.sin(angle), getAnimalName());
                int r = random.nextInt(4000) + 1000;
                System.out.println("\t" +getAnimalName() + " will sleep " + r + " milliseconds...");
                sleep(r);
                System.out.println("\t" + getAnimalName() + " wake up...");
            }catch (InterruptedException ex) {
                System.out.println("\t" + getAnimalName() + " interrupted");
                interrupt();
            }
        }
    }

}

