package validator.checkers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validator.annotations.Size;

import static org.junit.jupiter.api.Assertions.*;

class SizeCheckerTest {

    @Size(min = 2, max = 4)
    String value = "aaa";

    SizeChecker checker;
    Size annotation;

    @BeforeEach
    void setUp() throws NoSuchFieldException {

        annotation = this.getClass().getDeclaredField("value")
                .getAnnotatedType().getAnnotation(Size.class);

        checker = new SizeChecker();
    }

    @Test
    void isValid() {
        assertTrue(checker.isValid(value, annotation));

        assertTrue(checker.isValid(null, annotation));

        assertFalse(checker.isValid("a", annotation));

        double d = 5;
        assertFalse(checker.isValid(d, annotation));

    }

    @Test
    void createValidatorError() {

        assertFalse(checker.isValid("a", annotation));

        System.out.println("@Test SizeChecker.createValidatorError");

        System.out.println("\t" + checker.createValidatorError("a", annotation, "").getMessage());

    }

}