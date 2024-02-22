package com.rohith.ecommercemobilefashionapp;

import android.net.Uri;

import java.util.ArrayList;

public  class ProductSchema {

    Uri ImageUrl;

    public String selectedSize;

    public String getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    public ArrayList<String> getSize() {
        return size;
    }

    public Uri getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(Uri imageUrl) {
        ImageUrl = imageUrl;
    }

    public String productName;
    public String price;
    public String category;
    public String description;
    public String id;
    public String gender;
    public ArrayList<String> size;
    public String brand;
    public String available;
    public String sellerName;
    public String sellerCompany;
    public String product_img;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public ProductSchema(Uri imageUrl, String productName, String price, String category, String description, String id, String gender, ArrayList<String> size, String brand, String available, String sellerName, String sellerCompany, String product_img) {
        this.ImageUrl = imageUrl;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.description = description;
        this.id = id;
        this.gender = gender;
        this.size = size;
        this.brand = brand;
        this.available = available;
        this.sellerName = sellerName;
        this.sellerCompany = sellerCompany;
        this.product_img = product_img;
    }

    public void setSize(ArrayList<String> size) {
        this.size = size;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerCompany() {
        return sellerCompany;
    }

    public void setSellerCompany(String sellerCompany) {
        this.sellerCompany = sellerCompany;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductSchema() {
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
