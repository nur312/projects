package validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The value must be negative. Can be used for byte, short, int, long and their wrappers.
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Checker(getCheckerClass = "validator.checkers.NegativeChecker")
public @interface Negative {
}
