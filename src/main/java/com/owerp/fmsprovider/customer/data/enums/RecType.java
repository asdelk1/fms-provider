package com.owerp.fmsprovider.customer.data.enums;

public enum RecType {

    N("Normal"),
    L("Level"),
    R("Reconciled");

    private String text;
    /**
     * @param text text
     */
    RecType(final String text) {
        this.text = text;
    }

    /**
     * @return text
     */
    public String getText() {
        return this.text;
    }
}
