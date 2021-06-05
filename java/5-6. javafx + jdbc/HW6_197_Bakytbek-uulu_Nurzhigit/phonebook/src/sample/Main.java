package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.MainController;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("controllers/view/Main.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        Scene scene = new Scene(root);
        controller.setPrimaryStage(stage);
        stage.setScene(scene);
        stage.setOnHidden(e -> controller.shutdown());

        stage.setTitle("Phonebook");

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
