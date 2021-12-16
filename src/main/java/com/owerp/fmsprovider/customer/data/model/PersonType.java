package com.owerp.fmsprovider.customer.data.model;

public enum PersonType {

    NONE("None"),
    CUSTOMER("Customer"),
    SUPPLIER("Supplier");

    private String text;
    /**
     * @param text text
     */
    PersonType(final String text) {
        this.text = text;
    }

    /**
     * @return text
     */
    public String getText() {
        return this.text;
    }
}
