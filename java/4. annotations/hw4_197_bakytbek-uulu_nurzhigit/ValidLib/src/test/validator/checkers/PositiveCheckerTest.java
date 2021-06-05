package validator.checkers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validator.annotations.Negative;
import validator.annotations.Positive;

import static org.junit.jupiter.api.Assertions.*;

class PositiveCheckerTest {

    @Positive
    int value = 5;

    PositiveChecker checker;
    Positive annotation;

    @BeforeEach
    void setUp() throws NoSuchFieldException {

        annotation = this.getClass().getDeclaredField("value")
                .getAnnotatedType().getAnnotation(Positive.class);

        checker = new PositiveChecker();
    }

    @Test
    void isValid() {
        assertTrue(checker.isValid(value, annotation));
        value = -11;
        assertFalse(checker.isValid(value, annotation));

        double d = 5;

        assertFalse(checker.isValid(d, annotation));

    }

    @Test
    void createValidatorError() {
        String s = "5";
        assertFalse(checker.isValid(s, annotation));

        System.out.println("@Test PositiveChecker.createValidatorError");

        System.out.println("\t" + checker.createValidatorError(s, annotation, "").getMessage());
        int i = -1;
        assertFalse(checker.isValid(i, annotation));

        System.out.println("\t" + checker.createValidatorError(i, annotation, "").getMessage());
    }
}