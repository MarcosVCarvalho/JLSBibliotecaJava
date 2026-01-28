package com.felipemeireles.sistemadebiblioteca;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/felipemeireles/sistemadebiblioteca/view/Biblioteca.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1550, 785);

        stage.setTitle("Biblioteca Jaime Laurindo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}