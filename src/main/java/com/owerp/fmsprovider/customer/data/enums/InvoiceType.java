package com.owerp.fmsprovider.customer.data.enums;

public enum InvoiceType {

    COMMERCIAL("Commercial"),
    TAX("Tax"),
    SVAT("SVAT"),
    NORMAL("Normal");

    private String text;
    /**
     * @param text text
     */


    InvoiceType(final String text) {
        this.text = text;
    }

    /**
     * @return text
     */
    public String getText() {
        return this.text;
    }
}
