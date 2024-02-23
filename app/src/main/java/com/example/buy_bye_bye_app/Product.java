package com.example.buy_bye_bye_app;
/**
 * Represents a product with its image, name, price, and quantity.
 * This class is used to model the details of a product within an application, typically for e-commerce platforms,
 * inventory management systems, or any system that requires tracking of product details.
 */
public class Product {

    // Fields to store the product's details
    private String Image;// URL or reference to the product's image
    private String Name;// Name of the product
    private String Price; // Price of the product
    private String Quantity;// Quantity of the product available

    /**
     * Default constructor for initializing a Product object without setting its properties.
     * Useful when you need to create a Product object and set its fields later.
     */
    public Product(){}

    /**
     * Constructor to initialize a Product object with specified details.
     *
     * @param image URL or reference to the product's image.
     * @param name Name of the product.
     * @param price Price of the product.
     * @param quantity Quantity of the product available.
     */
    public Product(String image, String name, String price, String quantity) {
        Image = image;
        Name = name;
        Price = price;
        Quantity = quantity;
    }

    // Getter and setter methods for each field
    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
