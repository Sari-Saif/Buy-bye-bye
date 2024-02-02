package com.example.buy_bye_bye_app;

public class Product {

    private String Image;
    private String Name;
    private String Price;
    private String Quantity;

    public Product(){}

    public Product(String image, String name, String price, String quantity) {
        Image = image;
        Name = name;
        Price = price;
        Quantity = quantity;
    }

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