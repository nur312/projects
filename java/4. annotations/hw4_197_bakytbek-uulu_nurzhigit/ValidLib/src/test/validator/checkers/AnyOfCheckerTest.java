package validator.checkers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validator.annotations.AnyOf;

import static org.junit.jupiter.api.Assertions.*;

class AnyOfCheckerTest {

    @AnyOf({"a", "b", "c"})
    String value;

    AnyOfChecker checker;

    AnyOf annotation;


    @BeforeEach
    void setUp() throws NoSuchFieldException {

        annotation = this.getClass().getDeclaredField("value").getAnnotatedType().getAnnotation(AnyOf.class);

        checker = new AnyOfChecker();
    }

    @Test
    void isValid() {
        value = "a";
        assertTrue(checker.isValid(value, annotation));

        value = "d";
        assertFalse(checker.isValid(value, annotation));

        value = null;
        assertTrue(checker.isValid(value, annotation));

    }

    @Test
    void createValidatorError() {
        value = "d";
        assertFalse(checker.isValid(value, annotation));

        var error = checker.createValidatorError(value,
                annotation, "");

        System.out.println("@Test AnyOfChecker.createValidatorError");
        System.out.println("\t" + error.getMessage());
    }
}