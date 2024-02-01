package com.example.buy_bye_bye_app;

public class Seller_user {


    private String email;
    private String password;
    private String address;
    private String bank;

    public Seller_user(String email, String password, String address, String bank) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.bank = bank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

}
