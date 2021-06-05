package validator.checkers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validator.annotations.Validator;
import validator.checkers.classesfortest.BookingForm;
import validator.checkers.classesfortest.GuestForm;
import validator.checkers.classesfortest.TestClass;
import validator.checkers.classesfortest.Unrelated;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorImplTest {

    Validator validator;

    @BeforeEach
    void setUp() {

        validator = Fabric.createValidator();
    }

    @Test
    void validate() {

        // Fields initialized  in TestClass

        var t = new TestClass();

        var errors = validator.validate(t);

        assertEquals(13, errors.size());

    }

    @Test
    void sample() {

        List<GuestForm> guests = List.of(
                new GuestForm(/*firstName*/ null, /*lastName*/ "Def", /*age*/ 21),
                new GuestForm(/*firstName*/ "", /*lastName*/ "Ijk", /*age*/ -3)
        );
        Unrelated unrelated = new Unrelated(-1);
        BookingForm bookingForm = new BookingForm(
                guests,
                /*amenities*/ List.of("TV", "Piano"),
                /*propertyType*/ "Apartment",
                unrelated
        );
        var errors = validator.validate(bookingForm);

        assertEquals(5, errors.size());

    }
}