package validator.checkers;

import validator.annotations.ValidationError;

import java.lang.annotation.Annotation;

class PositiveChecker implements IChecker {

    private String message;

    /**
     * Validates an object in accordance with an annotation.
     *  * The value must be positive. Can be used for byte, short, int, long and their wrappers.
     *
     * @param obj               object
     * @param currentAnnotation annotation
     * @return result of validating
     */
    @Override
    public boolean isValid(Object obj, Annotation currentAnnotation) {

        if (obj instanceof Byte) {
            return (Byte) obj > 0;
        } else if (obj instanceof Short) {
            return (Short) obj > 0;
        } else if (obj instanceof Integer) {
            return (Integer) obj > 0;
        } else if (obj instanceof Long) {
            return (Long) obj > 0;
            //System.out.println(((Long) o).longValue());
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
            message = "Must be positive.";
        }

        return new ValidatorErrorImpl(message, path, obj);
    }
}
