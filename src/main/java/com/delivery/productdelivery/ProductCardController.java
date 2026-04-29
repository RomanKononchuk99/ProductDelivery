package com.delivery.productdelivery;

import com.delivery.productdelivery.data.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class ProductCardController {

    @FXML
    private Label productName;

    @FXML
    private Label productDescription;

    @FXML
    private Label productPrice;


    public void setData(Product product) {
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(product.getPrice() + " $");

//        if (product.getImageUrl() != null) {
//            productImage.setImage(new Image(product.getImageUrl()));
//        }

//        addButton.setOnAction(e -> {
//            System.out.println("Добавлено: " + product.getName());
//        });
    }


}
