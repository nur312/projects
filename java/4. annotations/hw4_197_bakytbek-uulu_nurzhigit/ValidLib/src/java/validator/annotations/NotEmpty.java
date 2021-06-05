package validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The value must not be empty.
 * Can apply to List<T>, Set<T>, Map<K, V>, String.
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Checker(getCheckerClass = "validator.checkers.NotEmptyChecker")
public @interface NotEmpty {
}
