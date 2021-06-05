package validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The l value must be in the array specified in the annotation.
 * Can apply to String.
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Checker(getCheckerClass = "validator.checkers.AnyOfChecker")
public @interface AnyOf {

    String[] value();
}
