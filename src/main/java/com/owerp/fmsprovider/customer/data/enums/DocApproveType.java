package com.owerp.fmsprovider.customer.data.enums;

public enum DocApproveType {

    NONE("None"),
    CHECKED("Checked"),
    APPROVED("Approved"),
    APPROVE_REJECTED("Approve rejected"),
    CHECK_REJECTED("Check rejected");


    private String text;
    /**
     * @param text text
     */
    DocApproveType(final String text) {
        this.text = text;
    }

    /**
     * @return text
     */
    public String getText() {
        return this.text;
    }
}
