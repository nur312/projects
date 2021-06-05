package validator.checkers;

import validator.annotations.Size;
import validator.annotations.ValidationError;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class SizeChecker implements IChecker {

    String message;

    /**
     * Validates an object in accordance with an annotation.
     *  * The size of the annotated element must be in the range [min, max].
     *  * Can apply for List<T>, Set<T>, Map<K, V>, String.
     * @param obj               object
     * @param currentAnnotation annotation
     * @return result of validating
     */
    @Override
    public boolean isValid(Object obj, Annotation currentAnnotation) {

        int size;

        var annotation = (Size) currentAnnotation;

        if (obj instanceof List) {
            size = ((List<?>) obj).size();

        } else if (obj instanceof Set) {
            size = ((Set<?>) obj).size();

        } else if (obj instanceof Map) {
            size = ((Map<?, ?>) obj).size();

        } else if (obj instanceof String) {
            size = ((String) obj).length();

        } else if (obj == null) {

            return true;
        } else {
            message = "Must be List, Set, Map or String.";
            return false;
        }

        return annotation.min() <= size && size <= annotation.max();
    }

    /**
     * Create instance of ValidationError with messages.
     *
     * @param obj               failed object
     * @param currentAnnotation annotation
     * @param path              path
     * @return error
     */
    @Override
    public ValidationError createValidatorError(Object obj, Annotation currentAnnotation, String path) {

        var annotation = (Size) currentAnnotation;

        return new ValidatorErrorImpl(Objects.requireNonNullElseGet(message, () -> "Size must be in range ["
                + annotation.min() + ", " + annotation.max() + "]"), path, obj);
    }
}
