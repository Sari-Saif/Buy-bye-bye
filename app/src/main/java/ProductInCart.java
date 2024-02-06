public class ProductInCart {

    String Name;
    String Amount;

    String Price; // unit price

    int TotalCartPrice;

    public ProductInCart(String name, String amount, String price) {
        Name = name;
        Amount = amount;
        Price = price;

        TotalCartPrice = Integer.parseInt(Amount) * Integer.parseInt(Price);
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

    public int getTotalCartPrice() {
        return TotalCartPrice;
    }

    public void setTotalCartPrice(int totalCartPrice) {
        TotalCartPrice = totalCartPrice;
    }
}
