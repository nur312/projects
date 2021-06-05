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

public class ImportController {


    /**
     * Задется в MainController
     * @param allPersons
     */
    public void setAllPersons(ObservableList<Person> allPersons) {
        this.allPersons = allPersons;
    }

    ObservableList<Person> allPersons;

    /**
     * Задется в MainController
     * @param primaryStage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    Stage primaryStage;

    File file;

    @FXML
    private TextField fileNameIn;

    @FXML
    private Button chooseFileBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button okBtn;

    /**
     * Закрываем окно импорта.
     * @param event
     */
    @FXML
    void cancelBtnClicked(ActionEvent event) {

        ((Stage) cancelBtn.getScene().getWindow()).close();
    }

    /**
     * Выбираем файл для импорта.
     * @param event
     */
    @FXML
    void chooseFileBtnClicked(ActionEvent event) {

        var fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files ( ; separator)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {

            fileNameIn.setText(file.getPath());
        }

    }

    /**
     * Импортируем файл.
     * @param event
     */
    @FXML
    void okBtnClicked(ActionEvent event) {
        if (file != null) {

            Logic.importPersons(file.getPath(), allPersons);

            file = null;

            ((Stage) cancelBtn.getScene().getWindow()).close();
        } else {

            fileNameIn.setText(null);
            fileNameIn.setPromptText("Заново выбрите файл, пожалуйста");
        }


    }
}
