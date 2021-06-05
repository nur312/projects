package sample.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.Logic;
import sample.PhonebookDb;
import sample.models.Person;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PersonAddController {
    /**
     * Задется в MainController.
     * @param persons контакты.
     */
    public void setPersons(ObservableList<Person> persons) {
        this.persons = persons;
    }

    ObservableList<Person> persons;

    @FXML
    private TextField nameIn;

    @FXML
    private TextField surnameIn;

    @FXML
    private TextField phoneIn;

    @FXML
    private TextField addressIn;

    @FXML
    private TextArea commentIn;

    @FXML
    private DatePicker birthdayDP;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button addBtn;

    /**
     * Выводим подсказки и выделяем красным цветом при невалидных данных.
     */
    void validateInputs() {


        if (nameIn.getText().isEmpty() || nameIn.getText().isBlank()) {

            System.err.println("Empty name");
            nameIn.setPromptText("Пустое значение");

            nameIn.setStyle("-fx-text-box-border: #B22222;");
        }
        if (surnameIn.getText().isEmpty() || surnameIn.getText().isBlank()) {

            System.err.println("Empty surname");
            surnameIn.setPromptText("Пустое значение");
            surnameIn.setStyle("-fx-text-box-border: #B22222;");
        }
        if (persons.stream().anyMatch(p -> p.getName().equals(nameIn.getText())
                && p.getSurname().equals(surnameIn.getText()))) {


            var tmp = nameIn.getText() + " " + surnameIn.getText() + " уже есть в контактах";

            System.err.println(tmp);
            surnameIn.setPromptText(tmp);
            nameIn.setPromptText(tmp);

            surnameIn.setStyle("-fx-text-box-border: #B22222;");
            nameIn.setStyle("-fx-text-box-border: #B22222;");
            surnameIn.setText(null);
            nameIn.setText(null);
        }
        if (phoneIn.getText().isEmpty() || phoneIn.getText().isBlank()) {

            System.err.println("Empty phone number");
            phoneIn.setPromptText("Пустое значение");
            phoneIn.setStyle("-fx-text-box-border: #B22222;");
        }
    }

    /**
     * Добавляем новый контакт.
     * @param event
     */
    @FXML
    void addBtnClicked(ActionEvent event) {
        surnameIn.setStyle("-fx-text-box-border: #b5aeae;");
        nameIn.setStyle("-fx-text-box-border: #b5aeae;");
        phoneIn.setStyle("-fx-text-box-border: #b5aeae;");



        if (!Logic.isCorrectToAdd(nameIn.getText(), surnameIn.getText(), phoneIn.getText(), persons,
                addressIn.getText(), commentIn.getText())) {

            validateInputs();
            return;
        }


        Person person = new Person();

        person.setName(nameIn.getText())
                .setSurname(surnameIn.getText())
                .setPhone(phoneIn.getText())
                .setAddress(addressIn.getText() != null ? addressIn.getText() : "")
                .setComment(commentIn.getText() != null ? commentIn.getText() : "")
                .setDate(birthdayDP.getValue());

        persons.add(person);
        PhonebookDb.addPerson(person);

        ((Stage) addBtn.getScene().getWindow()).close();
    }

    /**
     * Закрываем текущее окно.
     * @param event
     */
    @FXML
    void cancelBtnClicked(ActionEvent event) {

        ((Stage) cancelBtn.getScene().getWindow()).close();
    }

}
