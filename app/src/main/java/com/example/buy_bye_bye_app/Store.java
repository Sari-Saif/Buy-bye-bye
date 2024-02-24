package com.example.buy_bye_bye_app;
/**
 * Represents a store with its name.
 * This class is designed to model basic store information, primarily focused on the store's name,
 * within an application. It can be used in various contexts where store-related data needs to be managed
 * or displayed, such as in lists of stores in a shopping app.
 */
public class Store {
    // Field to store the name of the store
    String StoreName;
    String sum_ratings;
    String total_raters;

    public String getSum_ratings() {
        return sum_ratings;
    }

    public String getTotal_raters() {
        return total_raters;
    }

    /**
     * Retrieves the name of the store.
     *
     * @return The name of the store as a String.
     */
    public String getStoreName() {
        return StoreName;
    }


}
