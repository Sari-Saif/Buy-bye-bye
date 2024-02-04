package com.example.buy_bye_bye_app;

public class ActiveOrder {
    private String OrderID;
    private String StoreName;

    public ActiveOrder() {}

    public ActiveOrder(String id, String name) {
        this.OrderID = id;
        this.StoreName = name;
    }

    public String getOrderID() { return OrderID; }

    public void setOrderID(String orderID) { this.OrderID = orderID; }

    public String getStoreName() { return StoreName; }

    public void setStoreName(String storeName) { this.StoreName = storeName; }
}
