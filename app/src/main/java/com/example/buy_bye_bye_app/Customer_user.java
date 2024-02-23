package com.example.buy_bye_bye_app;
/**
 * Represents a customer user with their email, password, address, and visa information.
 * This class is used to model the customer's information within an application.
 */
public class Customer_user {
    // Fields to store the customer's information
    private String email;
    private String password;
    private String address;
    private String visa;
    private String id;
    /**
     * Constructor to initialize a customer user with their email, password, address, and visa information.
     *
     * @param email The customer's email address.
     * @param password The customer's password for authentication.
     * @param address The customer's physical address.
     * @param visa The customer's visa information.
     */
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
    /**
     * Returns a string representation of the Customer_user object, including all fields.
     *
     * @return A string representation of the customer user.
     */
    @Override
    public String toString() {
        return "Customer_user{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", visa='" + visa + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}