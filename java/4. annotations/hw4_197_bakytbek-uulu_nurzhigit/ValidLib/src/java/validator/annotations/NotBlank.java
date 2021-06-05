package validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Invalid if the string is empty or contains only white space codepoints, otherwise false.
 * Can be apply for String.
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Checker(getCheckerClass = "validator.checkers.NotBlankChecker")
public @interface NotBlank {
}
