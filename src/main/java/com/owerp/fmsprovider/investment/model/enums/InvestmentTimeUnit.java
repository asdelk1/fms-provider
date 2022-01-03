package com.owerp.fmsprovider.investment.model.enums;

public enum InvestmentTimeUnit {

    DAYS("Days"),
    WEEKS("Weeks"),
    MONTHS("Months");

    private String text;
    /**
     * @param text text
     */
    InvestmentTimeUnit(final String text) {
        this.text = text;
    }

    /**
     * @return text
     */
    public String getText() {
        return this.text;
    }
}
