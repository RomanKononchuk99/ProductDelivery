package com.delivery.productdelivery;

import com.delivery.productdelivery.data.Product;
import com.delivery.productdelivery.service.ProductService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private Button fullWindowButton;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public ListView<List<Product>> productsList;

    @FXML
    private VBox container;

    ProductService productService;

    @FXML
    private void initialize(){
        productService = new ProductService();
        loadProducts();
        productsList.setPrefHeight(Region.USE_COMPUTED_SIZE);
        productsList.setMaxHeight(Region.USE_PREF_SIZE);

        Image exitButton = new Image("file:src/main/resources/images/exit_button.png");
        Image minimalizeButton = new Image("file:src/main/resources/images/minimilize_button.png");

        ImageView exitButtonView = new ImageView(exitButton);
        ImageView minimalizeButtonView = new ImageView(minimalizeButton);

        exitButtonView.setFitWidth(25);
        exitButtonView.setFitHeight(25);

        minimalizeButtonView.setFitHeight(25);
        minimalizeButtonView.setFitWidth(25);

        minimalizeButtone.setGraphic(minimalizeButtonView);
        exitButtone.setGraphic(exitButtonView);
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



    private Node createProductCard(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/delivery/productdelivery/product-card.fxml")
            );

            Parent card = loader.load();

            ProductCardController controller = loader.getController();
            controller.setData(product);

            return card;

        } catch (Exception e) {
            e.printStackTrace();
            return new Label("Ошибка");
        }
    }


    private void loadProducts() {
        Task<List<Product>> task = new Task<>() {
            @Override
            protected List<Product> call() throws Exception {
                return productService.getProducts();
            }
        };

        task.setOnSucceeded(e -> {
            List<Product> products = task.getValue();

            for (Product product : products) {
                Node card = createProductCard(product);
                container.getChildren().add(card);
            }

        });

        task.setOnFailed(e -> task.getException().printStackTrace());



        new Thread(task).start();
    }

    private List<List<Product>> groupProducts(List<Product> products, int size) {
        List<List<Product>> result = new ArrayList<>();

        for (int i = 0; i < products.size(); i += size) {
            result.add(products.subList(i, Math.min(i + size, products.size())));
        }

        return result;
    }






}
