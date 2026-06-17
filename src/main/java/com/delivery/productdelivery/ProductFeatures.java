package com.delivery.productdelivery;

import com.delivery.productdelivery.data.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public enum ProductFeatures {
    INSTANCE;

    final int pageSize = 10;

    public Node createProductCard(Product product) {
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

    public Node createPage(int pageIndex, List<Product> allProducts){
        VBox wrapper = new VBox();

        wrapper.setAlignment(Pos.TOP_CENTER);


        VBox pageBox = new VBox(10);
        pageBox.setMaxWidth(600);


        int from = pageIndex * pageSize;
        int to = Math.min(from + pageSize, allProducts.size());

        List<Product> pageItems = allProducts.subList(from, to);

        for (Product product : pageItems) {
            Node card = createProductCard(product);
            pageBox.getChildren().add(card);
        }

        wrapper.getChildren().add(pageBox);

        return wrapper;

    }

    public void loadMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(new Scene(root,800,1200));
        stage.show();
        stage.setResizable(false);
    }

    public void loadProductInfo(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("product-info.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(new Scene(root,800,1200));
        stage.show();
        stage.setResizable(false);
    }
}
