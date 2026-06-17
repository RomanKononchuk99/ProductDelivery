package com.delivery.productdelivery;

import com.delivery.productdelivery.data.Product;
import com.delivery.productdelivery.service.ProductService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button minimalizeButtone;

    @FXML
    private Button exitButtone;

    @FXML
    private Pagination productsPages;

    @FXML
    private Button fullWindowButton;

    @FXML
    private Button basketButton;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onBasketButton(ActionEvent event) throws IOException {



        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("basket.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(new Scene(root,800,1200));
        stage.show();
        stage.setResizable(false);

    }

    @FXML
    private VBox container;

    ProductService productService;

    int pageSize = 10;

    private List<Product> allProducts = new ArrayList<>();

    @FXML
    private void initialize(){
        productService = new ProductService();
        loadProducts();

        Image exitButton = new Image("file:src/main/resources/images/exit_button.png");
        Image minimalizeButton = new Image("file:src/main/resources/images/minimilize_button.png");
        Image resize = new Image("file:src/main/resources/images/resize_icon.png");
        Image basket = new Image("file:src/main/resources/images/basket_icon.png");


        ImageView exitButtonView = new ImageView(exitButton);
        ImageView minimalizeButtonView = new ImageView(minimalizeButton);
        ImageView resizeButtonView = new ImageView(resize);
        ImageView basketButtonView = new ImageView(basket);

        exitButtonView.setFitWidth(25);
        exitButtonView.setFitHeight(25);

        minimalizeButtonView.setFitHeight(25);
        minimalizeButtonView.setFitWidth(25);

        resizeButtonView.setFitWidth(25);
        resizeButtonView.setFitHeight(25);

        basketButtonView.setFitHeight(25);
        basketButtonView.setFitWidth(25);

        minimalizeButtone.setGraphic(minimalizeButtonView);
        exitButtone.setGraphic(exitButtonView);
        fullWindowButton.setGraphic(resizeButtonView);
        basketButton.setGraphic(basketButtonView);

    }

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






    private void loadProducts() {
        Task<List<Product>> task = new Task<>() {
            @Override
            protected List<Product> call() throws Exception {
                return productService.getProducts();
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
