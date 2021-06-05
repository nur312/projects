package validator.checkers;

import validator.annotations.InRange;
import validator.annotations.ValidationError;

import java.lang.annotation.Annotation;

class InRangeChecker implements IChecker {

    private String message;

    /**
     * Validates an object in accordance with an annotation.
     *  * The value must be in the range [min; max].
     *  * Can apply for byte, short, int, long and their wrappers.
     *
     * @param obj               object
     * @param currentAnnotation annotation
     * @return result of validating
     */
    @Override
    public boolean isValid(Object obj, Annotation currentAnnotation) {

        var annotation = (InRange) currentAnnotation;

        if (obj instanceof Byte) {

            return (Byte) obj >= annotation.min() && (Byte) obj <= annotation.max();
        } else if (obj instanceof Short) {

            return (Short) obj >= annotation.min() && (Short) obj <= annotation.max();
        } else if (obj instanceof Integer) {

            return (Integer) obj >= annotation.min() && (Integer) obj <= annotation.max();
        } else if (obj instanceof Long) {

            return (Long) obj >= annotation.min() && (Long) obj <= annotation.max();
        } else if (obj == null) {
            return true;
        } else {
            message = "Must be number: byte, short, int, long or their wrappers.";
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

        if (message == null) {
            var annotation = (InRange) currentAnnotation;

            message = "Must be in range [" + annotation.min() + ", " + annotation.max() + "].";
        }

        return new ValidatorErrorImpl(message, path, obj);
    }
}
