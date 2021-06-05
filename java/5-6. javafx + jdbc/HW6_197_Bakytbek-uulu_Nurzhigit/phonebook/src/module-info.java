module phonebook {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.apache.derby.tools;
    requires java.sql;

    opens sample;
    opens sample.controllers;
    opens sample.models;
    opens sample.controllers.view;

}