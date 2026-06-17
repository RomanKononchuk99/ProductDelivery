package com.delivery.productdelivery;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductInfoController {

    @FXML
    private Button minimalizeButtone;

    @FXML
    private Button exitButtone;

    @FXML
    private Button fullWindowButton;

    @FXML
    protected void onCloseWindowButtonClick(){
        Platform.exit();
    }

    @FXML
    protected void onMinimalizeButtonClick(){
        Stage stage = (Stage) minimalizeButtone.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    protected void onFullWidowButtonClick(){
        Stage stage = (Stage) fullWindowButton.getScene().getWindow();
        stage.setFullScreen(true);

    }

    @FXML
    protected void ontoMainButtonClick(ActionEvent event) throws IOException {

        ProductFeatures.INSTANCE.loadMain(event);

    }

}