package validator.checkers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validator.annotations.NotEmpty;

import static org.junit.jupiter.api.Assertions.*;

class NotEmptyCheckerTest {
    @NotEmpty
    String value = "a";

    NotEmptyChecker checker;
    NotEmpty annotation;

    @BeforeEach
    void setUp() throws NoSuchFieldException {

        annotation = this.getClass().getDeclaredField("value")
                .getAnnotatedType().getAnnotation(NotEmpty.class);

        checker = new NotEmptyChecker();
    }

    @Test
    void isValid() {
        assertTrue(checker.isValid(value, annotation));
        assertTrue(checker.isValid(null, annotation));
        value = "";
        assertFalse(checker.isValid(value, annotation));

        double d = 5;
        assertFalse(checker.isValid(d, annotation));

    }

    @Test
    void createValidatorError() {
        String s = "";
        assertFalse(checker.isValid(s, annotation));

        System.out.println("@Test NotEmptyChecker.createValidatorError");

        System.out.println("\t" + checker.createValidatorError(s, annotation, "").getMessage());

        double d = 4;
        assertFalse(checker.isValid(d, annotation));

        System.out.println("\t" + checker.createValidatorError(d, annotation, "").getMessage());


    }
}