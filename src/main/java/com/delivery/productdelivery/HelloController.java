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
import javafx.scene.control.*;
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
    private Pagination productsPages;

    @FXML
    private Button fullWindowButton;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
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
            allProducts = task.getValue();

            int pageCount = (int) Math.ceil((double) allProducts.size() / pageSize);
            productsPages.setPageCount(pageCount);

            productsPages.setPageFactory(this::createPage);
        });

        new Thread(task).start();

    }

    private List<List<Product>> groupProducts(List<Product> products, int size) {
        List<List<Product>> result = new ArrayList<>();

        for (int i = 0; i < products.size(); i += size) {
            result.add(products.subList(i, Math.min(i + size, products.size())));
        }

        return result;
    }

    private Node createPage(int pageIndex){

        VBox pageBox = new VBox(10);

        int from = pageIndex * pageSize;
        int to = Math.min(from + pageSize, allProducts.size());

        List<Product> pageItems = allProducts.subList(from, to);

        for (Product product : pageItems) {
            Node card = createProductCard(product);
            pageBox.getChildren().add(card);
        }

        return pageBox;

    }

}
