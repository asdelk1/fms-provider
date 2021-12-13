package com.owerp.fmsprovider.customer.data.enums;

public enum EntryType {

    CREDIT("Credit"),
    DEBIT("Debit");

    private String text;
    /**
     * @param text text
     */
    EntryType(final String text) {
        this.text = text;
    }

    /**
     * @return text
     */
    public String getText() {
        return this.text;
    }
}
