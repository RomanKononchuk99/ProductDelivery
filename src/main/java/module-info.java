module com.delivery.productdelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires javafx.graphics;
    requires javafx.base;


    opens com.delivery.productdelivery to javafx.fxml;
    opens com.delivery.productdelivery.data to com.google.gson;
    exports com.delivery.productdelivery;
    exports com.delivery.productdelivery.data;
}