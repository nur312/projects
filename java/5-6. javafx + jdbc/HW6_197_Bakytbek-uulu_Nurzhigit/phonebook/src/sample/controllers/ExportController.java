package sample.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Logic;
import sample.models.Person;

import java.io.File;


public class ExportController {


    /**
     * Задается через MainController
     * @param allPersons все контакты
     */
    public void setAllPersons(ObservableList<Person> allPersons) {
        this.allPersons = allPersons;
    }

    ObservableList<Person> allPersons;

    /**
     * Задается через MainController
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    Stage primaryStage;

    File file;

    @FXML
    private TextField fileNameIn;


    @FXML
    private Button cancelBtn;

    /**
     * Закрывает текущее окно экспорта
     */
    @FXML
    void cancelBtnClicked(ActionEvent event) {

        ((Stage) cancelBtn.getScene().getWindow()).close();
    }

    /**
     * Выбираем путь для экпорта.
     */
    @FXML
    void chooseFileBtnClicked(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files ( ; separator)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {

            fileNameIn.setText(file.getPath());
        }

    }

    /**
     * Экспортируем и закрываем текущее окно.
     */
    @FXML
    void okBtnClicked(ActionEvent event) {
        if (file != null) {

            Logic.saveToFile(allPersons, file.getPath());

            file = null;

            ((Stage) cancelBtn.getScene().getWindow()).close();
        } else {

            fileNameIn.setText(null);
            fileNameIn.setPromptText("Заново выбрите файл, пожалуйста");
        }


    }
}
