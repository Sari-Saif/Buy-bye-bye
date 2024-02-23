package com.example.buy_bye_bye_app;
/**
 * Represents a product added to a cart, including its name, amount, unit price, and total price.
 * This class is designed to model the attributes of a product as it exists within a shopping cart context,
 * specifically for e-commerce or retail applications.
 */
public class ProductInCart {

    // Fields to store the product's name, amount, and unit price
  String Name;
  String Amount;
  String Price; // unit price

    // Calculated field to store the total price of the product based on the amount and unit price
    int TotalPrice;
    /**
     * Default constructor for initializing a ProductInCart object without setting its properties.
     * Useful for situations where product attributes are set post object creation.
     */
    public ProductInCart(){}
    /**
     * Constructor to initialize a ProductInCart object with specified name, amount, and price.
     * It also calculates the total price based on the amount and unit price provided.
     *
     * @param name The name of the product.
     * @param amount The amount of the product being purchased.
     * @param price The unit price of the product.
     */
    public ProductInCart(String name, String amount, String price) {
        Name = name;
        Amount = amount;
        Price = price;

        // Calculate the total price based on amount and unit price
        TotalPrice = Integer.parseInt(Amount) * Integer.parseInt(Price);
    }

    // Getter and setter methods for each field
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    /**
     * Returns the total price of the product based on its amount and unit price.
     *
     * @return The calculated total price as an integer.
     */
    public int getTotalPrice() {

        // Recalculate the total price to ensure it's always accurate with the current amount and price
        return Integer.parseInt(this.Amount) * Integer.parseInt(this.Price);
    }

    /**
     * Sets the total price of the product. Note: This method's utility is limited
     * since the total price should ideally be calculated from the amount and unit price.
     *
     * @param totalPrice The total price to set.
     */
    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }
}
