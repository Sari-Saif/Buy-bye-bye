package com.example.buy_bye_bye_app;

public class Customer_user {

    private String email;
    private String password;
    private String address;
    private String visa;

    private String id;

    public Customer_user()
    {}
    public Customer_user(String email, String password, String address, String visa, String id) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.visa = visa;
        this.id = id;
    }

    public Customer_user(String email, String password, String address, String visa) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.visa = visa;
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

    public String getVisa() {
        return visa;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.visa = id;
    }


}
