package com.example.buy_bye_bye_app;
/**
 * Represents an active order with its associated details.
 */
public class ActiveOrder {
    // Unique identifier for the order
    private String OrderID;
    // Name of the store where the order was placed
    private String StoreName;
    // Name of the customer who placed the order
    private String CustomerName;
    /**
     * Default constructor for creating an instance without setting any initial values.
     */
    public ActiveOrder() {}
    /**
     * Constructs an ActiveOrder with specified details.
     *
     * @param id The unique identifier for the order.
     * @param name The name of the store where the order was placed.
     * @param customer The name of the customer who placed the order.
     */
    public ActiveOrder(String id, String name, String customer) {
        this.OrderID = id;
        this.StoreName = name;
        this.CustomerName = customer;
    }
    /**
     * Gets the order ID.
     *
     * @return A string representing the order ID.
     */
    public String getOrderID() { return OrderID; }
    /**
     * Sets the order ID.
     *
     * @param orderID The unique identifier for the order.
     */
    public void setOrderID(String orderID) { this.OrderID = orderID; }
    /**
     * Gets the name of the store.
     *
     * @return A string representing the name of the store.
     */
    public String getStoreName() { return StoreName; }
    /**
     * Sets the name of the store.
     *
     * @param storeName The name of the store where the order was placed.
     */
    public void setStoreName(String storeName) { this.StoreName = storeName; }
    /**
     * Gets the name of the customer who placed the order.
     *
     * @return A string representing the customer's name.
     */
    public String getCustomerName() { return CustomerName; }
    /**
     * Sets the name of the customer.
     *
     * @param customerName The name of the customer who placed the order.
     */
    public void setCustomerName(String customerName) { CustomerName = customerName; }
}
