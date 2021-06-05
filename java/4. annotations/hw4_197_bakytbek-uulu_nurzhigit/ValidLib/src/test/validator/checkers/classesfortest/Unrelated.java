package validator.checkers.classesfortest;

import validator.annotations.Positive;

// Нет аннотации @Constrained, поэтому проверке не подвергается
public class Unrelated {
    @Positive
    private int x;
    public Unrelated(int x) {
        this.x = x;
    }
}