package com.owerp.fmsprovider.customer.data.enums;

public enum CustomerFormulaType {

    NONE("None"),
    FIXED("Fixed"),
    SLAB("Slab");

    private final String label;
    /**
     * @param text text
     */
    CustomerFormulaType(final String text) {
        this.label = text;
    }

    /**
     * @return text
     */
    public String getLabel() {
        return this.label;
    }
}
