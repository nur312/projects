package validator.checkers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validator.annotations.NotNull;

import static org.junit.jupiter.api.Assertions.*;

class NotNullCheckerTest {
    @NotNull
    String value = "a";

    NotNullChecker checker;
    NotNull annotation;

    @BeforeEach
    void setUp() throws NoSuchFieldException {

        annotation = this.getClass().getDeclaredField("value")
                .getAnnotatedType().getAnnotation(NotNull.class);

        checker = new NotNullChecker();
    }

    @Test
    void isValid() {
        assertTrue(checker.isValid(value, annotation));

        assertFalse(checker.isValid(null, annotation));

        double d = 5;
        assertTrue(checker.isValid(d, annotation));

    }

    @Test
    void createValidatorError() {

        assertFalse(checker.isValid(null, annotation));

        System.out.println("@Test NotNullChecker.createValidatorError");

        System.out.println("\t" + checker.createValidatorError(null, annotation, "").getMessage());


    }
}