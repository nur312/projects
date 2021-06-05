package sample.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Logic;
import sample.PhonebookDb;
import sample.models.Person;

public class PersonEditorController {
    /**
     * Задется в MainController.
     * @param persons контакты.
     */
    public void setPersons(ObservableList<Person> persons) {
        this.persons = persons;
        init();
    }

    private ObservableList<Person> persons;

    public void setPerson(Person person) {
        this.person = person;
    }

    private Person person;


    @FXML
    private TextField nameIn;

    @FXML
    private TextField surnameIn;

    @FXML
    private TextField phoneIn;

    @FXML
    private TextField addressIn;

    @FXML
    private DatePicker birthdayDP;

    @FXML
    private TextArea commentIn;

    @FXML
    private Button saveBtn;
    /**
     * Выводим подсказки и выделяем красным цветом при невалидных данных.
     */
    void validateInputs(){

        if(persons.stream().anyMatch(p -> p != person && p.getName().equals(nameIn.getText())
                && p.getSurname().equals(surnameIn.getText()))){


            String tmp = nameIn.getText() + " " + surnameIn.getText() + " уже есть в контактах";

            System.err.println(tmp);
            surnameIn.setPromptText(tmp);
            nameIn.setPromptText(tmp);


            surnameIn.setStyle("-fx-text-box-border: #B22222;");
            nameIn.setStyle("-fx-text-box-border: #B22222;");
            surnameIn.setText(null);
            nameIn.setText(null);
        }
        if(nameIn.getText().isEmpty() || nameIn.getText().isBlank()){

            System.err.println("Empty name");
            nameIn.setPromptText("Пустое значение");
            nameIn.setStyle("-fx-text-box-border: #B22222;");

        }
        if(surnameIn.getText().isEmpty() || surnameIn.getText().isBlank()){

            System.err.println("Empty surname");
            surnameIn.setPromptText("Пустое значение");
            surnameIn.setStyle("-fx-text-box-border: #B22222;");
        }

        if(phoneIn.getText().isEmpty() || phoneIn.getText().isBlank()){

            System.err.println("Empty phone number");
            phoneIn.setPromptText("Пустое значение");
            phoneIn.setStyle("-fx-text-box-border: #B22222;");
        }
    }

    /**
     * Сохраняем измения.
     * @param event ивент.
     */
    @FXML
    void saveBtnClicked(ActionEvent event) {

        surnameIn.setStyle("-fx-text-box-border: #b5aeae;");
        nameIn.setStyle("-fx-text-box-border: #b5aeae;");
        phoneIn.setStyle("-fx-text-box-border: #b5aeae;");

        System.out.println("saveBtnClicked");

        if(!Logic.isCorrectToSave(nameIn.getText(), surnameIn.getText(), phoneIn.getText(), person,persons,
                addressIn.getText(), commentIn.getText())) {
            validateInputs();
            return;
        }

        person.setName(nameIn.getText())
                .setSurname(surnameIn.getText())
                .setPhone(phoneIn.getText())
                .setAddress(addressIn.getText() != null ? addressIn.getText() : "")
                .setComment(commentIn.getText() != null ? commentIn.getText() : "")
                .setDate(birthdayDP.getValue());

        PhonebookDb.updatePerson(person);

        ((Stage)saveBtn.getScene().getWindow()).close();

    }

    /**
     * Возращаем в поля для ввода начальные данные.
     * @param event ивент.
     */
    @FXML
    void cancelBtnClicked(ActionEvent event) {
        init();
    }

    /**
     * Инициализуем поля для ввода данными контакта.
     */
    private void init(){
        nameIn.setText(person.getName());
        surnameIn.setText(person.getSurname());
        phoneIn.setText(person.getPhone());
        addressIn.setText(person.getAddress());
        commentIn.setText(person.getComment());
        birthdayDP.setValue(person.getDate());
    }
}
