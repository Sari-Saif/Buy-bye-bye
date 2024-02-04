package com.example.buy_bye_bye_app;

public class ActiveOrderItem {
    private String Amount;
    private String Name;
    private String Price;

    public ActiveOrderItem() {
    }

    public ActiveOrderItem(String amount, String name, String price) {
        Amount = amount;
        Name = name;
        Price = price;
    }

    public String getAmount() { return Amount; }

    public String getName() { return Name; }

    public String getPrice() { return Price; }

    public void setAmount(String amount) { Amount = amount; }

    public void setName(String name) { Name = name; }

    public void setPrice(String price) { Price = price; }
}
