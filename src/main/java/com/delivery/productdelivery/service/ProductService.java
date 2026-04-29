package com.delivery.productdelivery.service;

import com.delivery.productdelivery.data.Product;
import com.delivery.productdelivery.data.ProductResponse;
import com.google.gson.Gson;
import network.ApiClient;

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

}
