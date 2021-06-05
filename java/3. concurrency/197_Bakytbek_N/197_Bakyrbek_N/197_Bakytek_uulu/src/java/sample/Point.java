package sample;

public final class Point {
    private double x, y;
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    Point() {
    }
    /**
     * Shifts point
     * @param dx x's shift
     * @param dy y's shift
     * @param name name of animal
     */
    public synchronized void Shift(double dx, double dy, String name) {
        x += dx;
        y += dy;
        System.out.println("\t" + name + " moved point to " +
                "(" + String.format("%.2f", x) + "; " + String.format("%.2f", y) + ")");
    }

    /**
     * Returns point coordinates
     * @return point coordinates
     */
    public synchronized String getCoordinates() {
        return "(" + String.format("%.2f", x) + "; " + String.format("%.2f", y) + ")";
    }
}

