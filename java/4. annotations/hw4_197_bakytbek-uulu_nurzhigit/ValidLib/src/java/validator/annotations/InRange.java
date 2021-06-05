package validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The value must be in the range [min; max].
 * Can apply for byte, short, int, long and their wrappers.
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Checker(getCheckerClass = "validator.checkers.InRangeChecker")
public @interface InRange {
    long min();
    long max();
}
