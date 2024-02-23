package com.example.buy_bye_bye_app;
/**
 * Represents a seller with their email, password, address, bank details, and an ID.
 * This class is used to model a seller's information within an application, typically for e-commerce platforms,
 * allowing for the management of seller accounts, including authentication and banking details for transactions.
 */
public class Seller {
    // Fields to store seller's information
    private String email;// Seller's email address for login and contact
    private String password;// Seller's password for authentication
    private String address;// Physical address of the seller
    private String bank;// Bank details of the seller for transactions
    private String id;// Unique identifier for the seller
    /**
     * Constructor to initialize a Seller object with specified details.
     *
     * @param email Seller's email address.
     * @param password Seller's password.
     * @param address Seller's physical address.
     * @param bank Seller's bank details.
     */
    public Seller(String email, String password, String address, String visa) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.bank = visa;
    }

    // Getter and setter methods for each field
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
    /**
     * Returns a string representation of the Seller object, including all fields.
     *
     * @return A string representation of the seller.
     */
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


