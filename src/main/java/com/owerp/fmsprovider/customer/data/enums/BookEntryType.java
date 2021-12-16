package com.owerp.fmsprovider.customer.data.enums;

public enum BookEntryType {

    SALES_INVOICE("Sales invoice"),
    JOURNAL_ENTRY("Journal entry"),
    CREDIT_NOTE("Credit note"),
    ADVANCE_RECEIVED("Advance received"),
    SALARY_DEDUCTION("Salary deduction"),
    SALARY_ALLOWANCE("Salary allowance"),
    EMPLOYEE_LOAN("Employee loan"),
    SALARY_TAX("Salary Tax"),
    PURCHASE_INVOICE("Purchase invoice"),
    DEBIT_NOTE("Debit note"),
    ADVANCE_PAYMENT("Advance payment"),
    CUSTOMER_PAYMENT("Customer received"),
    SUPPLIER_PAYMENT("Supplier received"),
    CESS_INVOICE("CESS invoice"),
    BANK_DEPOSIT("Bank deposit"),
    BANK_PAYMENT("Bank payment"),
    FUND_TRANSFER("Bank payment");

    private String text;
    /**
     * @param text text
     */
    BookEntryType(final String text) {
        this.text = text;
    }

    /**
     * @return text
     */
    public String getText() {
        return this.text;
    }
}
