package com.rohith.ecommercemobilefashionapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserSchema {
    String username;
    String phone = null;
    String email;
    String password;
    ArrayList<String> cart = null;
    ArrayList<String> recentlyViewed = null;
    ArrayList<String> orders = null;
    String isUser = "1";
    Address address = null;

    public UserSchema(String username, String phone, String email, String password, ArrayList<String> cart, ArrayList<String> recentlyViewed, ArrayList<String> orders, String isUser, Address address) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.cart = cart;
        this.recentlyViewed = recentlyViewed;
        this.orders = orders;
        this.isUser = isUser;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getCart() {
        return cart;
    }

    public void setCart(ArrayList<String> cart) {
        this.cart = cart;
    }

    public ArrayList<String> getRecentlyViewed() {
        return recentlyViewed;
    }

    public void setRecentlyViewed(ArrayList<String> recentlyViewed) {
        this.recentlyViewed = recentlyViewed;
    }

    public ArrayList<String> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<String> orders) {
        this.orders = orders;
    }

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserSchema() {
    }

    public Map<String, Object> toMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", username);
        userMap.put("phone", phone);
        userMap.put("email", email);
        userMap.put("password", password);
        userMap.put("cart", cart);
        userMap.put("recentlyViewed", recentlyViewed);
        userMap.put("orders", orders);
        userMap.put("address",address.toMap());
        userMap.put("isUser",isUser);
        return userMap;
    }

}

