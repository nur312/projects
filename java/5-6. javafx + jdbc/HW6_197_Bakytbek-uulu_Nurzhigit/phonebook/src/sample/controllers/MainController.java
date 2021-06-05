package sample.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Logic;
import sample.PhonebookDb;
import sample.models.Person;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    /**
     * Задается в Main
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    Stage primaryStage;

    @FXML
    TableView.TableViewSelectionModel<Person> selectionModel;
    /**
     * Контейнер для хранения всех контактов.
     */
    ObservableList<Person> allPersons;

    /**
     * Представление данных в виде таблицы.
     */
    @FXML
    private TableView<Person> personsTable;

    @FXML
    private Button addBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchIn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    /**
     * Удаляем контакт.
     */
    @FXML
    void deleteBtnClicked(ActionEvent event) {
        var selected = selectionModel.getSelectedItem();
        if( selected != null) {

            personsTable.getItems().remove(selected);
            PhonebookDb.deletePerson(selected);

        } else {
            System.out.println("Not selected");
        }
    }

    /**
     * Редактруем контакт.
     */
    @FXML
    void editBtnClicked(ActionEvent event) {
        var selected = selectionModel.getSelectedItem();
        if( selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "view/PersonEditor.fxml"));
                Parent root = loader.load();
                PersonEditorController controller = loader.getController();
                controller.setPerson(selected);
                controller.setPersons(allPersons);

                Stage stage = new Stage();
                stage.setTitle("Редактирование контакта");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(searchBtn.getScene().getWindow());
                stage.show();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Not selected");
        }

    }

    /**
     * Строка для поиска.
     * @param event
     */
    @FXML
    void searchBtnClicked(ActionEvent event) {
        System.out.println("searchBtnClicked");

        personsTable.setItems(Logic.filter(allPersons, searchIn.getText()));
    }

    /**
     * Добавляем новый контакт.
     * @param eventt
     */
    @FXML
    void addBtnClicked(ActionEvent eventt) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "view/PersonAdd.fxml"));
            Parent root = loader.load();
            PersonAddController controller = loader.getController();
            controller.setPersons(allPersons);

            Stage stage = new Stage();
            stage.setTitle("Добавление нового контакта");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(addBtn.getScene().getWindow());
            stage.show();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Инициализируем таблицу.
     */
    private void createTable(){

        var nameColumn = new TableColumn<Person, String>("Имя");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        var surnameColumn = new TableColumn<Person, String>("Фамилия");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        var phoneColumn = new TableColumn<Person, String>("Телефон");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        var addressColumn = new TableColumn<Person, String>("Адрес");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        var birthdayColumn = new TableColumn<Person, String>("День рождения");
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        var commentColumn = new TableColumn<Person, String>("Комментарий");
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));


        personsTable.getColumns().add(nameColumn);
        personsTable.getColumns().add(surnameColumn);
        personsTable.getColumns().add(phoneColumn);
        personsTable.getColumns().add(addressColumn);
        personsTable.getColumns().add(birthdayColumn);
        personsTable.getColumns().add(commentColumn);

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        PhonebookDb.getConnection();

        createTable();
        selectionModel = personsTable.getSelectionModel();

        allPersons = PhonebookDb.getAllPerson();

        personsTable.setItems(allPersons);

        personsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                deleteBtn.setStyle("-fx-text-fill: grey");
                editBtn.setStyle("-fx-text-fill: grey");
            } else {
                deleteBtn.setStyle("-fx-text-fill: black");
                editBtn.setStyle("-fx-text-fill: black");
            }
        });

        deleteBtn.setStyle("-fx-text-fill: grey");
        editBtn.setStyle("-fx-text-fill: grey");

    }

    public void shutdown(){

        PhonebookDb.shutdownDb();
    }

    /**
     * Экспортируем контакты.
     */
    @FXML
    void exportClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "view/Export.fxml"));
            Parent root = loader.load();
            ExportController controller = loader.getController();
            controller.setAllPersons(allPersons);
            controller.setPrimaryStage(primaryStage);

            Stage stage = new Stage();
            stage.setTitle("Экспорт контактов");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(addBtn.getScene().getWindow());

            personsTable.setItems(allPersons);
            stage.show();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * импортируем контакты.
     */
    @FXML
    void importClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "view/Import.fxml"));
            Parent root = loader.load();
            ImportController controller = loader.getController();
            controller.setAllPersons(allPersons);
            controller.setPrimaryStage(primaryStage);

            Stage stage = new Stage();
            stage.setTitle("Импорт контактов");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(addBtn.getScene().getWindow());

            personsTable.setItems(allPersons);
            stage.show();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Выходим из приложения.
     */
    @FXML
    void exitClicked(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}