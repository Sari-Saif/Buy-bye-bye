package com.example.buy_bye_bye_app;

public class ActiveOrder {
    private String OrderID;
    private String StoreName;

    private String CustomerName;

    public ActiveOrder() {}

    public ActiveOrder(String id, String name, String customer) {
        this.OrderID = id;
        this.StoreName = name;
        this.CustomerName = customer;
    }

    public String getOrderID() { return OrderID; }

    public void setOrderID(String orderID) { this.OrderID = orderID; }

    public String getStoreName() { return StoreName; }

    public void setStoreName(String storeName) { this.StoreName = storeName; }

    public String getCustomerName() { return CustomerName; }

    public void setCustomerName(String customerName) { CustomerName = customerName; }
}
