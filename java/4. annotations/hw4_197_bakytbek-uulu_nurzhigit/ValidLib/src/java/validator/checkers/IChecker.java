package validator.checkers;


import validator.annotations.ValidationError;

import java.lang.annotation.Annotation;

interface IChecker {

    /**
     * Validates an object in accordance with an annotation.
     *
     * @param obj               object
     * @param currentAnnotation annotation
     * @return result of validating
     */
    boolean isValid(Object obj, Annotation currentAnnotation);

    /**
     * Create instance of ValidationError with messages.
     *
     * @param obj               failed object
     * @param currentAnnotation annotation
     * @param path              path
     * @return error
     */
    ValidationError createValidatorError(Object obj, Annotation currentAnnotation, String path);
}
