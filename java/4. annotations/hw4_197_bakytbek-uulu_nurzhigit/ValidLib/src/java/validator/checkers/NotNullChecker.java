package validator.checkers;

import validator.annotations.ValidationError;

import java.lang.annotation.Annotation;

class NotNullChecker implements IChecker {


    /**
     * Validates an object in accordance with an annotation.
     *  * The value must not be null.
     * @param obj               object
     * @param currentAnnotation annotation
     * @return result of validating
     */
    @Override
    public boolean isValid(Object obj, Annotation currentAnnotation) {
        return obj != null;
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
        return new ValidatorErrorImpl("Must be not null.", path, obj);
    }
}
