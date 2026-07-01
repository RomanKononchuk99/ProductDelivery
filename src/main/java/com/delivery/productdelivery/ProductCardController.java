package com.delivery.productdelivery;

import com.delivery.productdelivery.data.Product;
import com.delivery.productdelivery.service.ProductService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


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

    @FXML
    private AnchorPane productCardPane;

    @FXML
    private ImageView productImage;

    ProductService productService;

    Product productLocal;

    boolean isBasket;


    @FXML
    public void initialize(){
        productService = new ProductService();

        isBasket = productService.checkProductInBasket(productLocal);
        ProductFeatures.INSTANCE.changeButtonView(isBasket, addBasketButton);
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
        ProductFeatures.INSTANCE.changeButtonView(isBasket, addBasketButton);


    }

    @FXML
    protected void onProductCardPaneClick(MouseEvent event) throws IOException {
        Navigation.switchScene("/com/delivery/productdelivery/product-info.fxml", productCardPane, (ProductInfoController c) -> c.setProduct(productLocal));
    }


    public void setData(Product product) {
        Image image = new Image(product.getImageUrl().toString());
        productImage.setImage(image);
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(product.getPrice() + " $");
        productLocal = product;


        isBasket = productService.checkProductInBasket(productLocal);

        isBasket = productService.checkProductInBasket(productLocal);

//        if (product.getImageUrl() != null) {
//            productImage.setImage(new Image(product.getImageUrl()));
//        }

//        addButton.setOnAction(e -> {
//            System.out.println("Добавлено: " + product.getName());
//        });
    }




}
