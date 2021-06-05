package sample;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.models.Person;

class PhonebookDbTest {

    @BeforeEach
    void setUp() {

        PhonebookDb.getTestConnection();

        var p1 = new Person();
        p1.setName("a1").setSurname("b1").setPhone("c1");

        var p2 = new Person();
        p2.setName("a2").setSurname("b2").setPhone("c2");

        PhonebookDb.addPerson(p1);
        PhonebookDb.addPerson(p2);
    }

    @AfterEach
    void tearDown() {
        PhonebookDb.dropTestDb();
    }

    @Test
    void addPerson() {
        System.out.println("********addPerson********");

        var p3 = new Person();
        p3.setName("added person").setSurname("b3").setPhone("c3");

        System.out.println("testing:\t"+"before adding");
        printPeople();

        PhonebookDb.addPerson(p3);

        System.out.println("testing:\t"+"after adding");
        printPeople();

    }

    @Test
    void updatePerson() {
        System.out.println("********updatePerson********");

        var p3 = new Person();
        p3.setName("a3").setSurname("b3").setPhone("c3");

        PhonebookDb.addPerson(p3);

        p3.setName("updated name");

        System.out.println("testing:\t"+"before updating");
        printPeople();

        PhonebookDb.updatePerson(p3);

        System.out.println("testing:\t"+"after updating");
        printPeople();
    }

    @Test
    void deletePerson() {
        System.out.println("********deletePerson********");

        var p3 = new Person();
        p3.setName("a3").setSurname("b3").setPhone("c3");

        PhonebookDb.addPerson(p3);

        System.out.println("testing:\t"+"before deleting");
        printPeople();

        PhonebookDb.deletePerson(p3);

        System.out.println("testing:\t"+"after deleting");
        printPeople();
    }

    void printPeople() {

        var list = PhonebookDb.getAllPerson();
        System.out.println("testing:\t"+"printing people");
        for (var p : list) {
            System.out.println("testing:\t"+"id: " + p.getPersonId() + "; name: " + p.getName()
                    + "; surname: " + p.getSurname()
                    + "; phone: " + p.getPhone());
        }
    }
}