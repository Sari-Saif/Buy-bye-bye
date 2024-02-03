package com.example.buy_bye_bye_app;

public class Seller {
    private String email;
    private String password;
    private String address;
    private String bank;
    private String id;

    public Seller(String email, String password, String address, String visa) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.bank = visa;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer_user{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", bank='" + bank + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}


