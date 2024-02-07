package com.example.buy_bye_bye_app;

public class ProductInCart {

    String Name;
    String Amount;

    String Price; // unit price

    int TotalPrice;

    public ProductInCart(){}

    public ProductInCart(String name, String amount, String price) {
        Name = name;
        Amount = amount;
        Price = price;

        TotalPrice = Integer.parseInt(Amount) * Integer.parseInt(Price);
    }

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

    public int getTotalPrice() {
        return Integer.parseInt(this.Amount) * Integer.parseInt(this.Price);
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }
}
