package com.delivery.productdelivery;

import com.delivery.productdelivery.data.Product;
import com.delivery.productdelivery.service.ProductService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasketController {

    @FXML
    private Button minimalizeButtone;

    @FXML
    private Button exitButtone;

    @FXML
    private Button fullWindowButton;

    @FXML
    private Pagination productsPages;

    @FXML
    private Button toMainButton;

    ProductService productService;

    private List<Product> allProducts = new ArrayList<>();

    int pageSize = 10;

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




    @FXML
    private void initialize(){
        productService = new ProductService();
        loadProducts();

        Image exitButton = new Image("file:src/main/resources/images/exit_button.png");
        Image minimalizeButton = new Image("file:src/main/resources/images/minimilize_button.png");
        Image resize = new Image("file:src/main/resources/images/resize_icon.png");

        ImageView exitButtonView = new ImageView(exitButton);
        ImageView minimalizeButtonView = new ImageView(minimalizeButton);
        ImageView resizeButtonView = new ImageView(resize);

        exitButtonView.setFitWidth(25);
        exitButtonView.setFitHeight(25);

        minimalizeButtonView.setFitHeight(25);
        minimalizeButtonView.setFitWidth(25);

        resizeButtonView.setFitWidth(25);
        resizeButtonView.setFitHeight(25);

        minimalizeButtone.setGraphic(minimalizeButtonView);
        exitButtone.setGraphic(exitButtonView);
        fullWindowButton.setGraphic(resizeButtonView);

    }


    private void loadProducts() {
        Task<List<Product>> task = new Task<>() {
            @Override
            protected List<Product> call() throws Exception {
                return productService.loadBasketProducts();
            }
        };

        task.setOnSucceeded(e -> {
            allProducts = task.getValue();


            int pageCount = (int) Math.ceil((double) allProducts.size() / pageSize);
            productsPages.setPageCount(pageCount);


            productsPages.setPageFactory(pageIndex ->
                    ProductFeatures.INSTANCE.createPage(pageIndex,allProducts));
        });

        new Thread(task).start();

    }



}
