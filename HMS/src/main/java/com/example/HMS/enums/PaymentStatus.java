package com.example.HMS.enums;

public enum PaymentStatus {

    PAID("Payment completed"),
    UNPAID("Payment pending"),
    PARTIAL("Partially paid"),
    REFUNDED("Payment refunded"),
    CANCELLED("Payment cancelled");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
