package com.mercadolibre.integradora2;

import com.mercadolibre.integradora2.model.Manager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class MainApplication extends Application {
    private static final Manager manager = new Manager();

    @Override
    public void start(Stage stage) {
        renderView("main-view.fxml", "Mercado Libre",400, 300);
    }

    public static void main(String[] args) {
        launch();
    }

    public static void renderView(String fxml, String title, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Alert");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static Manager getManager() {
        return manager;
    }
}