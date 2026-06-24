package com.delivery.productdelivery;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

public class Navigation {

    public static <T> void switchScene(String fxml, Node node, Consumer<T> controllerInit) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Navigation.class.getResource(fxml)
            );

            Parent root = loader.load();

            T controller = loader.getController();
            controllerInit.accept(controller);

            Stage stage = (Stage) node.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
