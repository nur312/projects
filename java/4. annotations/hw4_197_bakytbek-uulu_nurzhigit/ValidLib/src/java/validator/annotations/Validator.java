package validator.annotations;

import java.util.Set;

public interface Validator {

    /**
     * Validates an object.
     *
     * @param object test object
     * @return errors
     */
    Set<ValidationError> validate(Object object);

    //    static Validator getValidator() {
    //        return new ValidatorImpl();
    //    }
}
