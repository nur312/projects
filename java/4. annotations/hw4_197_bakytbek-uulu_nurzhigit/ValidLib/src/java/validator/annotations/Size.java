package validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The size of the annotated element must be in the range [min, max].
 * Can apply for List<T>, Set<T>, Map<K, V>, String.
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Checker(getCheckerClass = "validator.checkers.SizeChecker")
public @interface Size {
    int min();
    int max();

}
