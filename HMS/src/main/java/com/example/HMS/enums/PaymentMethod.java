package com.example.HMS.enums;

public enum PaymentMethod {

    CASH("Cash payment"),
    CARD("Credit/Debit card"),
    UPI("UPI transfer"),
    NET_BANKING("Net banking"),
    INSURANCE("Insurance claim");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
