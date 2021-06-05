package validator.checkers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validator.annotations.Negative;

import static org.junit.jupiter.api.Assertions.*;

class NegativeCheckerTest {

    @Negative
    int value = -5;

    NegativeChecker checker;
    Negative annotation;

    @BeforeEach
    void setUp() throws NoSuchFieldException {

        annotation = this.getClass().getDeclaredField("value")
                .getAnnotatedType().getAnnotation(Negative.class);

        checker = new NegativeChecker();
    }

    @Test
    void isValid() {
        assertTrue(checker.isValid(value, annotation));
        value = 11;
        assertFalse(checker.isValid(value, annotation));

        double d = 5;

        assertFalse(checker.isValid(d, annotation));

    }

    @Test
    void createValidatorError() {
        String s = "5";
        assertFalse(checker.isValid(s, annotation));

        System.out.println("@Test NegativeChecker.createValidatorError");

        System.out.println("\t" + checker.createValidatorError(s, annotation, "").getMessage());
        int i = 1;
        assertFalse(checker.isValid(i, annotation));

        System.out.println("\t" + checker.createValidatorError(i, annotation, "").getMessage());
    }
}