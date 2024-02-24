package com.example.buy_bye_bye_app;
/**
 * Represents an individual order item with details such as amount, name, and price.
 */
public class ActiveOrderItem {
    // The amount of the item ordered.
    private String Amount;
    // The name of the item.
    private String Name;
    // The price of the item.
    private String Price;
    /**
     * Default constructor for creating an instance without setting properties immediately.
     */
    public ActiveOrderItem() {
    }
    /**
     * Constructs an ActiveOrderItem with specified amount, name, and price.
     *
     * @param amount The amount of the item ordered.
     * @param name   The name of the item.
     * @param price  The price of the item.
     */
    public ActiveOrderItem(String amount, String name, String price) {
        Amount = amount;
        Name = name;
        Price = price;
    }
    /**
     * Gets the amount of the item ordered.
     *
     * @return A string representing the amount of the item.
     */
    public String getAmount() { return Amount; }
    /**
     * Gets the name of the item.
     *
     * @return A string representing the name of the item.
     */
    public String getName() { return Name; }
    /**
     * Gets the price of the item.
     *
     * @return A string representing the price of the item.
     */
    public String getPrice() { return Price; }
    /**
     * Sets the amount of the item ordered.
     *
     * @param amount A string representing the amount to be set.
     */
    public void setAmount(String amount) { Amount = amount; }
    /**
     * Sets the name of the item.
     *
     * @param name A string representing the name to be set.
     */
    public void setName(String name) { Name = name; }
    /**
     * Sets the price of the item.
     *
     * @param price A string representing the price to be set.
     */
    public void setPrice(String price) { Price = price; }
}
