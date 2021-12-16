package com.owerp.fmsprovider.customer.data.enums;

public enum InvoicePaymentStatus {

    NONE("None"),
    PARTIALLY("Partially pay"),
    COMPLETE("Complete"),
    OVER("Over pay");


    private String text;
    /**
     * @param text text
     */
    InvoicePaymentStatus(final String text) {
        this.text = text;
    }

    /**
     * @return text
     */
    public String getText() {
        return this.text;
    }
}
