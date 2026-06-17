package com.delivery.productdelivery;

import com.delivery.productdelivery.data.Product;
import com.delivery.productdelivery.service.ProductService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class ProductCardController {

    @FXML
    private Label productName;

    @FXML
    private Label productDescription;

    @FXML
    private Label productPrice;

    @FXML
    private Button addBasketButton;

    ProductService productService;

    Product productLocal;

    boolean isBasket;


    @FXML
    public void initialize(){
        productService = new ProductService();



    }

    public void changeButtonView(){
        if (!isBasket){
            addBasketButton.setText("Добавить");
        }else {
            addBasketButton.setText("Удалить");
        }
    }

    @FXML
    protected void onAddBasketButtonClick(){
        if (isBasket){
            productService.removeProduct(productLocal);
        }else {
            productService.pushProduct(productLocal);
            addBasketButton.setText("Успешно))");
        }
        isBasket = productService.checkProductInBasket(productLocal);
        changeButtonView();


    }

    @FXML
    protected void onProductCardPaneClick(MouseEvent event) throws IOException {
        ProductFeatures.INSTANCE.loadProductInfo(event);
    }


    public void setData(Product product) {
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(product.getPrice() + " $");
        productLocal = product;

        isBasket = productService.checkProductInBasket(productLocal);

        changeButtonView();

//        if (product.getImageUrl() != null) {
//            productImage.setImage(new Image(product.getImageUrl()));
//        }

//        addButton.setOnAction(e -> {
//            System.out.println("Добавлено: " + product.getName());
//        });
    }




}
