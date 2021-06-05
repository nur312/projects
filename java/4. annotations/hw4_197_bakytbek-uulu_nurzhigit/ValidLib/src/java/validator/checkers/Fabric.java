package validator.checkers;

import validator.annotations.Validator;

public final class Fabric {

    public static Validator createValidator() {

        return new ValidatorImpl();
    }
}
