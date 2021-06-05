package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.models.Person;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    ObservableList<Person> persons;


    @BeforeEach
    void setUp() {
        var personA = new Person();

        personA.setName("A")
                .setSurname("Sur")
                .setPhone("46");

        var personB = new Person();

        personB.setName("B")
                .setSurname("Al")
                .setPhone("37");

        var personC = new Person();

        personC.setName("Anna")
                .setSurname("Sunar")
                .setPhone("46");



        persons = FXCollections.observableArrayList(personA, personB, personC);
    }

    /**
     * Проверяем логику доавления элемента в таблицу.
     */
    @Test
    void isCorrectToAdd() {

        assertTrue(Logic.isCorrectToAdd("C", "Da", "312",
                persons, null, null));

        assertFalse(Logic.isCorrectToAdd("A", "Sur", "14775",
                persons, null, null));



        assertFalse(Logic.isCorrectToAdd("A", null, "14775",
                persons, null, null));

        assertFalse(Logic.isCorrectToAdd(null, "null", "14775",
                persons, null, null));

        assertFalse(Logic.isCorrectToAdd("null", "null", null,
                persons, null, null));

        assertFalse(Logic.isCorrectToAdd("Blank", "null", "   ",
                persons, null, null));

        assertFalse(Logic.isCorrectToAdd("Empty", "null", "",
                persons, null, null));
    }

    /**
     * Проверяем логику сохранения редактированного элента.
     */
    @Test
    void isCorrectToSave() {

        assertTrue(Logic.isCorrectToSave("C", "Da", "312", persons.get(0),
                persons, null, null));

        assertTrue(Logic.isCorrectToSave("A", "Sur", "14775", persons.get(0),
                persons, null, null));


        assertFalse(Logic.isCorrectToSave("A", null, "14775", persons.get(0),
                persons, null, null));

        assertFalse(Logic.isCorrectToSave(null, "null", "14775", persons.get(0),
                persons, null, null));

        assertFalse(Logic.isCorrectToSave("null", "null", null, persons.get(0),
                persons, null, null));

    }

    /**
     * Проверяем поиск.
     */
    @Test
    void filter() {

        assertEquals(Logic.filter(persons, "A").size(), 3);

        assertEquals(Logic.filter(persons, "An").size(), 1);

        assertEquals(Logic.filter(persons, "Nan").size(), 0);

        for(var i : Logic.filter(persons, "B")){

            System.out.println(i.getName() + " " + i.getSurname());
        }
    }
}