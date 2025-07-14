package com.example.myapplication;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String title;
    private String description;
    private double price;
    private String category;
    private String imageUrl;
    private double ratingRate;
    private int ratingCount;

    public Product(int id, String title, String description, double price,
                   String category, String imageUrl, double ratingRate, int ratingCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.ratingRate = ratingRate;
        this.ratingCount = ratingCount;
    }

    // getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }
    public double getRatingRate() { return ratingRate; }
    public int getRatingCount() { return ratingCount; }
}
