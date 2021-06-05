package validator.checkers;

import validator.annotations.AnyOf;
import validator.annotations.ValidationError;

import java.lang.annotation.Annotation;
import java.util.*;

class AnyOfChecker implements IChecker {

    String message;

    /**
     * Validates an object in accordance with an annotation.
     *
     *  * The value must be in the array specified in the annotation.
     *  * Can apply to String.
     *
     * @param obj               object
     * @param currentAnnotation annotation
     * @return result of validating
     */
    @Override
    public boolean isValid(Object obj, Annotation currentAnnotation) {


        var annotation = (AnyOf) currentAnnotation;

        if (obj instanceof String) {

            String string = (String) obj;

            for (var s : annotation.value()) {
                if (s.equals(string)) {
                    return true;
                }
            }

            return false;
        } else if (obj == null) {

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

        var annotation = (AnyOf) currentAnnotation;

        return new ValidatorErrorImpl(Objects.requireNonNullElseGet(message, () -> "Value must be any of " +
                Arrays.toString(annotation.value())), path, obj);
    }
}
