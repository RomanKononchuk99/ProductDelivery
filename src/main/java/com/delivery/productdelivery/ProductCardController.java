package com.delivery.productdelivery;

import com.delivery.productdelivery.data.Product;
import com.delivery.productdelivery.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

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

    public void initialize(){
        productService = new ProductService();

        isBasket = productService.checkProductInBasket(productLocal);

        changeButtonView();

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


    public void setData(Product product) {
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(product.getPrice() + " $");
        productLocal = product;

//        if (product.getImageUrl() != null) {
//            productImage.setImage(new Image(product.getImageUrl()));
//        }

//        addButton.setOnAction(e -> {
//            System.out.println("Добавлено: " + product.getName());
//        });
    }




}
