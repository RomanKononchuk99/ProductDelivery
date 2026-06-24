package com.delivery.productdelivery;

import com.delivery.productdelivery.data.Product;
import com.delivery.productdelivery.service.ProductService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ImageView productImageField;

    @FXML
    private Label productNameField;

    @FXML
    private Label productDescriptionField;

    @FXML
    private Label productPriceField;

    @FXML
    private Button addBasketButton;

    Product productLocal;

    ProductService productService;

    boolean isBasket;


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
    protected void onAddBasketButton() {
        if (isBasket) {
            productService.removeProduct(productLocal);
        } else {
            productService.pushProduct(productLocal);
            addBasketButton.setText("Успешно))");
        }
        isBasket = productService.checkProductInBasket(productLocal);
    }


    @FXML
    private void initialize(){

        productService = new ProductService();

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

        ProductFeatures.INSTANCE.changeButtonView(isBasket, addBasketButton);

    }

    public void setProduct(Product product){
        productLocal = product;
        System.out.println(productLocal.getName());
        setProductDetails();
    }

    public void setProductDetails(){
//        productImageField.setImage(productLocal.getImageUrl());
        System.out.println(productLocal.getImageUrl().toString());
        Image image = new Image(productLocal.getImageUrl().toString());
        productImageField.setImage(image);
        productNameField.setText(productLocal.getName());
        productDescriptionField.setText(productLocal.getDescription());
        productPriceField.setText(productLocal.getPrice().toString() + "$");

    }

}