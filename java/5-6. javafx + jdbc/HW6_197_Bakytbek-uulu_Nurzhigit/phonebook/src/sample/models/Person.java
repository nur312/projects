package sample.models;

import javafx.beans.property.*;
import sample.Logic;

import java.time.LocalDate;

public class Person {
    public long getPersonId() {
        return personId;
    }

    public Person setPersonId(long personId) {
        this.personId = personId;
        return this;
    }

    long personId;


    private SimpleStringProperty surname = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty phone = new SimpleStringProperty();
    private SimpleStringProperty address = new SimpleStringProperty();
    private SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private SimpleStringProperty comment = new SimpleStringProperty();



    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public Person setSurname(String surname) {
        this.surname.set(surname);
        return this;
    }



    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public Person setName(String name) {
        this.name.set(name);
        return this;
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public Person setPhone(String phone) {
        this.phone.set(phone);
        return this;
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public Person setAddress(String address) {
        this.address.set(address);
        return this;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public Person setDate(LocalDate date) {
        this.date.set(date);
        return this;
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public Person setComment(String comment) {
        this.comment.set(comment);
        return this;
    }

    @Override
    public String toString() {
        return  name.get() +
                Logic.separator + surname.get() +
                Logic.separator + phone.get() +
                Logic.separator + (address.get() != null ? address.get() : "") +
                Logic.separator + (date.get() != null ? date.get().format(Logic.formatter) : "") +
                Logic.separator + (comment.get() != null ? comment.get() : "");
    }


}
