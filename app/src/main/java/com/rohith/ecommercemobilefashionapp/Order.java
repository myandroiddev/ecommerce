package com.rohith.ecommercemobilefashionapp;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    String userName;
    String userId;
    String email;
    String phone;
    Address address;
    Date orderDate;
    ArrayList<ProductSchema> products;
    boolean couponApplied;
    Date deliveriedDate;

    public Order(String userName, String userId, String email, String phone, Address address, Date orderDate, ArrayList<ProductSchema> products, boolean couponApplied, Date deliveriedDate) {
        this.userName = userName;
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.orderDate = orderDate;
        this.products = products;
        this.couponApplied = couponApplied;
        this.deliveriedDate = deliveriedDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public ArrayList<ProductSchema> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductSchema> products) {
        this.products = products;
    }

    public boolean isCouponApplied() {
        return couponApplied;
    }

    public void setCouponApplied(boolean couponApplied) {
        this.couponApplied = couponApplied;
    }

    public Date getDeliveriedDate() {
        return deliveriedDate;
    }

    public void setDeliveriedDate(Date deliveriedDate) {
        this.deliveriedDate = deliveriedDate;
    }
}
