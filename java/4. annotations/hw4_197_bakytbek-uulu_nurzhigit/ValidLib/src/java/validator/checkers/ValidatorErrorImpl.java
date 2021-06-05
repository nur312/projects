package validator.checkers;

import validator.annotations.ValidationError;

/**
 * Implementation of ValidationError.
 */
class ValidatorErrorImpl implements ValidationError {

    final String message;
    final String path;
    final Object failedValue;

    public ValidatorErrorImpl(String message, String path, Object failedValue) {
        this.message = message;
        this.path = path;
        this.failedValue = failedValue;
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object getFailedValue() {
        return failedValue;
    }
}
