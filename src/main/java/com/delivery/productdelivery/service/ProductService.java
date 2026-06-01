package com.delivery.productdelivery.service;

import com.delivery.productdelivery.data.Product;
import com.delivery.productdelivery.data.ProductResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import network.ApiClient;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductService {

    ApiClient apiClient = new ApiClient();
    private final Gson gson = new Gson();

    public List<Product> getProducts() throws Exception {

        String json = apiClient.get("/products");
        ProductResponse response = gson.fromJson(json, ProductResponse.class);
        return Arrays.asList(response.getProducts());
    }

    public void pushProduct(Product product){

        List<Product> productList = loadBasketProducts();
        productList.add(product);
        saveProduct(productList);
    }


    public void removeProduct(Product product){

        List<Product> productList = loadBasketProducts();
        productList.remove(product);
        saveProduct(productList);
    }

    public List<Product> loadBasketProducts(){
        try {
            File file = new File("src/main/resources/basketProducts.json");
            if (!file.exists()){
                return new ArrayList<>();
            }
            Reader reader = new FileReader(file);
            Type type = new TypeToken<List<Product>>(){}.getType();

            List<Product> products = gson.fromJson(reader, type);

            reader.close();

            return products != null ? products : new ArrayList<>();

        }catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveProduct(List<Product> products){
        try {

            File file = new File("src/main/resources/basketProducts.json");

            file.getParentFile().mkdirs();

            Writer writer = new FileWriter(file);

            gson.toJson(products, writer);

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean checkProductInBasket(Product product){
        return loadBasketProducts().contains(product);
    }

}
