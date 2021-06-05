package validator.checkers;

import validator.annotations.ValidationError;

import java.lang.annotation.Annotation;
import java.util.Objects;

class NotEmptyChecker implements IChecker {

    private String message;

    /**
     * Validates an object in accordance with an annotation.
     *  * The value must not be empty.
     *  * Can apply to List<T>, Set<T>, Map<K, V>, String.
     *
     * @param obj               object
     * @param currentAnnotation annotation
     * @return result of validating
     */
    @Override
    public boolean isValid(Object obj, Annotation currentAnnotation) {

        if (obj instanceof String) {
            return !((String) obj).isEmpty();
        } else if(obj == null){
            return true;
        } else {
            message = "Must be String.";
            return false;
        }
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
        return new ValidatorErrorImpl(Objects.requireNonNullElse(message, "Must be not empty."), path, obj);
    }
}
