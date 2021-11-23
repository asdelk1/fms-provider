package com.owerp.fmsprovider.helper.model.enums;

public enum TaxOperator {
    INCLUSIVE("Inclusive"),
    EXCLUSIVE("Exclusive");

    private final String text;
    /**
     * @param text text
     */
    TaxOperator(final String text) {
        this.text = text;
    }

    /**
     * @return text
     */
    public String getText() {
        return this.text;
    }
}
